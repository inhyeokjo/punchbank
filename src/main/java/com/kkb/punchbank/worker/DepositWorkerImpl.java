package com.kkb.punchbank.worker;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.dto.wiretransfer.api.DepositCallbackReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepositWorkerImpl implements DepositWorker{
	private final AccountService accountService;
	private final TransferService transferService;
	private final GUIDGenerator guidGenerator;


	@Transactional
	@Override
	public void deposit(WireTransferDepositReqDto requestDto, String transferId) {
		log.info("start transaction for deposit");
		String transactionHistoryId = guidGenerator.getGuid();
		PbAccount account = accountService.getAccountWithLock(requestDto.getDeposit().getAccountNumber());

		PbTransfer transfer = PbTransfer.depositTransferFrom(requestDto, transactionHistoryId, transferId, requestDto.getTransactionId());
		transferService.saveTransfer(transfer);

		accountService.checkDeposit(account, requestDto.getDeposit().getUsername());

		transferService.changeTransferProgress(transfer, TransferProgress.CHECKED);

		transfer.setBalance(account.plusAmount(requestDto.getAmount()));
		transfer.setTransferProgress(TransferProgress.SUCCESS);
		log.info("commit transaction for deposit");
	}

	@Override
	public void informDepositSuccess(String callbackUrl, String wireTransferId) {
		inform(callbackUrl, DepositCallbackReqDto.successDtoOf(wireTransferId));
	}

	@Override
	public void informDepositFail(String callbackUrl, String wireTransferId, String message) {
		inform(callbackUrl, DepositCallbackReqDto.failDtoOf(wireTransferId, message));
	}

	private void inform(String callbackUrl, DepositCallbackReqDto dto) {
		WebClient webClient = WebClient.create(callbackUrl);
		log.info("inform deposit result to trading bank => {} {}\nbody:{}", "POST", callbackUrl, dto);
		webClient.post()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(dto), DepositCallbackReqDto.class)
				.retrieve()
				.bodyToMono(Void.class)
				.doOnSuccess(unused -> log.info("deposit result inform success (wireTransferId={})", dto.getTransactionId()))
				.onErrorResume(throwable -> {
					log.info("deposit result inform fail (wireTransferId={})", dto.getTransactionId());
					return Mono.error(throwable);
				})
				.timeout(Duration.ofSeconds(5))
				.retry(5)
				.block();
	}
}
