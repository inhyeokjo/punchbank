package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.wiretransfer.api.WireDepositReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtherBankDepositWorkerImpl implements OtherBankDepositWorker {
	private final BankService bankService;


	@Override
	public void otherBankDeposit(WireTransferReqDto requestDto, String wireTransferId,
								 Function<? super Throwable, ? extends Mono<Void>> postFailure) {
		String transferUrl = bankService.getTransferUrl(requestDto.getDeposit().getBankCode());
		WebClient webClient = WebClient.create(transferUrl);
		WireDepositReqDto wireDepositReqDto = WireDepositReqDto.from(requestDto, wireTransferId);
		log.info("other bank deposit request => {} {}\nbody:{}", "POST", transferUrl, wireDepositReqDto);
		webClient.post()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(wireDepositReqDto), WireDepositReqDto.class)
				.retrieve()
				.bodyToMono(Void.class)
				.timeout(Duration.ofSeconds(5))
				.doOnSuccess(unused -> log.info("request other bank deposit success (wireTransferId={})", wireTransferId))
				.onErrorResume(postFailure)
				.block();
	}
}
