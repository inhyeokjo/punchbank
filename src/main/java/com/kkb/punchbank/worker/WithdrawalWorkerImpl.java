package com.kkb.punchbank.worker;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.response.WireTransferResDto;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.biz.WireBankError;
import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import com.kkb.punchbank.service.pojo.WithdrawCheckDto;
import com.kkb.punchbank.service.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
@Slf4j
public class WithdrawalWorkerImpl implements WithdrawalWorker {
	private final GUIDGenerator guidGenerator;
	private final AccountService accountService;
	private final TransferService transferService;
	private final RequestValidator requestValidator;
	private final PlatformTransactionManager transactionManager;

	@Transactional
	@Override
	public WireTransferResDto withdraw(WireTransferReqDto requestDto, String wireTransferId) {
		log.info("start transaction for withdraw (wireTransferId={})", wireTransferId);
		String transactionHistoryId = guidGenerator.getGuid();
		String transferId = guidGenerator.generateTransferId();

		// 배타락 걸고 출금 계좌 조회
		PbAccount withdrawalAccount = accountService.getAccountWithLock(requestDto.getWithdrawal().getAccountNumber());

		// 출금 기록 생성
		PbTransfer withdrawalTransfer = PbTransfer.withdrawalTransferFrom(requestDto, transactionHistoryId, transferId, wireTransferId);
		transferService.saveTransfer(withdrawalTransfer);

		// 이체 전 검증
		WithdrawCheckDto withdrawCheckDto = new WithdrawCheckDto(requestDto.getAmount(), requestDto.getWithdrawal().getAccountHolder());
		accountService.checkWithdraw(withdrawalAccount, withdrawCheckDto);
		requestValidator.checkWireTransfer(requestDto);

		// 출금 기록 상태 : CHECKED
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.CHECKED);

		withdrawalTransfer.setBalance(withdrawalAccount.minusAmount(requestDto.getAmount()));
		// 출금 기록 상태 : SUCCESS
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.WITHDRAW_SUCCESS);
		log.info("end transaction for withdraw (wireTransferId={})", wireTransferId);

		return WireTransferResDto.of(transferId);
	}

	@Transactional
	@Override
	public void recoverWithdraw(String wireTransferId) {
		log.info("start transaction for recover withdraw (wireTransferId={})", wireTransferId);
		PbTransfer withdrawalTransfer = transferService.findByWireTransferId(wireTransferId);
		if (!withdrawalTransfer.isWithdrawSuccess()) {
			log.info("this wireTransfer progress state is {} (wireTransferId={})", withdrawalTransfer.getTransferProgress(), wireTransferId);
			return;
		}
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.FAIL);

		String withdrawalAccountNumber = withdrawalTransfer.getTransferId().getAccountNumber();
		PbAccount withdrawalAccount = accountService.getAccountWithLock(withdrawalAccountNumber);
		withdrawalTransfer.setBalance(withdrawalAccount.minusAmount(withdrawalTransfer.getAmount()));
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.RECOVER);
		log.info("end transaction for recover withdraw (wireTransferId={})", wireTransferId);
	}

	@Transactional
	@Override
	public void successWithdraw(String wireTransferId) {
		log.info("start transaction for wire transfer post processing(success) (wireTransferId={})", wireTransferId);
		PbTransfer transfer = transferService.findByWireTransferId(wireTransferId);
		if (!transfer.isWithdrawSuccess()) {
			log.info("this wireTransfer progress state is {} (wireTransferId={})", transfer.getTransferProgress(), transfer.getWireTransferId());
			return;
		}
		transfer.setTransferProgress(TransferProgress.SUCCESS);
		log.info("end transaction for wire transfer post processing(success) (wireTransferId={})", wireTransferId);
	}

	@Override
	public Function<Throwable, Mono<Void>> getRecoverStrategy(String wireTransferId) {
		return error -> {
			log.info("----------- withdrawal discard logic start -----------");
			recoverWithdrawWithTransaction(wireTransferId);
			log.info("-----------  withdrawal discard logic end  -----------");
			return Mono.error(new WireBankError(ExceptionMessage.WIRE_BANK_ERROR, error));
		};
	}

	private void recoverWithdrawWithTransaction(String wireTransferId) {
		TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			recoverWithdraw(wireTransferId);
			transactionManager.commit(transaction);
		} catch (RuntimeException e) {
			transactionManager.rollback(transaction);
			throw e;
		}
	}
}
