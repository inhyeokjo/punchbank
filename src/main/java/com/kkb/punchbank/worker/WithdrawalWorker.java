package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.response.WireTransferResDto;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.function.Function;

@Validated
public interface WithdrawalWorker {
	WireTransferResDto withdraw(WireTransferReqDto requestDto,@NotBlank String wireTransferId);
	void recoverWithdraw(@NotBlank String wireTransferId);
	void successWithdraw(@NotBlank String wireTransferId);

	Function<Throwable, Mono<Void>> getRecoverStrategy(@NotBlank String wireTransferId);
}
