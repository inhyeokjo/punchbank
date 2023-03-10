package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.function.Function;

@Validated
public interface OtherBankDepositWorker {
	void otherBankDeposit(WireTransferReqDto requestDto, @NotBlank String wireTransferId, Function<? super Throwable, ? extends Mono<Void>> postFailure);
}
