package com.kkb.punchbank.worker;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.dto.transfer.request.TransferCheckReqDto;
import com.kkb.punchbank.dto.transfer.request.TransferReqDto;
import com.kkb.punchbank.dto.transfer.response.TransferResDto;
import com.kkb.punchbank.global.exception.biz.InsufficientBalanceException;
import com.kkb.punchbank.global.exception.biz.NotActiveAccountException;
import com.kkb.punchbank.global.exception.forbidden.ForbiddenException;
import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import com.kkb.punchbank.service.pojo.WithdrawCheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransferWorkerImpl implements TransferWorker {
	private final GUIDGenerator guidGenerator;
	private final AccountService accountService;
	private final TransferService transferService;


	@Transactional
	@Retryable(value = CannotAcquireLockException.class, backoff = @Backoff(delay = 50))
	@Override
	public TransferResDto transfer(TransferReqDto requestDto) {
		log.info("start transaction for transfer");

		// transferId, amount 획득
		String transactionId = guidGenerator.getGuid();
		String transferId = guidGenerator.generateTransferId();
		BigDecimal amount = requestDto.getAmount();

		// 출금계좌와 입금계좌를 배타락을 걸고 조회
		PbAccount withdrawalAccount = accountService.getAccountWithLock(requestDto.getWithdrawal().getAccountNumber());
		PbAccount depositAccount = accountService.getAccountWithLock(requestDto.getDeposit().getAccountNumber());

		// 이체내역 테이블에 입금 기록과 출금 기록을 저장
		PbTransfer withdrawalTransfer = PbTransfer.withdrawalTransferFrom(requestDto, transferId, transactionId);
		PbTransfer depositTransfer = PbTransfer.depositTransferFrom(requestDto, transferId, transactionId);
		transferService.saveTransfer(withdrawalTransfer);
		transferService.saveTransfer(depositTransfer);

		// 이체 전 검증 로직 수행
		checkTransfer(TransferCheckReqDto.of(requestDto));

		// 출금 기록과 입금 기록의 상태를 CHECKED로 변경
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.CHECKED);
		transferService.changeTransferProgress(depositTransfer, TransferProgress.CHECKED);

		// 출금 계좌에서 돈을 빼고 출금 기록의 상태를 SUCCESS로 변경
		withdrawalTransfer.setBalance(withdrawalAccount.minusAmount(amount));
		transferService.changeTransferProgress(withdrawalTransfer, TransferProgress.SUCCESS);
		// 입금 계좌에 돈을 넣고 입금 기록의 상태를 SUCCESS로 변경
		depositTransfer.setBalance(depositAccount.plusAmount(amount));
		transferService.changeTransferProgress(depositTransfer, TransferProgress.SUCCESS);

		log.info("end transaction for transfer");
		return TransferResDto.of(transferId);
	}


	@Transactional(readOnly = true)
	@Override
	public void checkTransfer(TransferCheckReqDto requestDto)
			throws InsufficientBalanceException, ForbiddenException, NotActiveAccountException {
		log.info("start readOnly transaction for transfer pre checking");
		PbAccount withdrawalAccountDto = accountService.getAccount(requestDto.getWithdrawal().getAccountNumber());
		WithdrawCheckDto withdrawCheckDto = new WithdrawCheckDto(requestDto.getAmount(), requestDto.getWithdrawal().getAccountHolder());
		accountService.checkWithdraw(withdrawalAccountDto, withdrawCheckDto);

		PbAccount depositAccountDto = accountService.getAccount(requestDto.getDeposit().getAccountNumber());
		accountService.checkDeposit(depositAccountDto, requestDto.getDeposit().getAccountHolder());

		log.info("end readOnly transaction for transfer pre checking");
	}


	@Override
	public void changeTransferProgress(String wireTransferId, TransferProgress transferProgress) {
		transferService.changeTransferProgress(wireTransferId, transferProgress);
	}
}
