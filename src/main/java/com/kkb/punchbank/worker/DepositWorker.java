package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface DepositWorker {
	void deposit(WireTransferDepositReqDto requestDto, @NotBlank String transferId);
	void informDepositSuccess(@NotBlank String callbackUrl, @NotBlank String wireTransferId);
	void informDepositFail(@NotBlank String callbackUrl, @NotBlank String wireTransferId, String message);
}
