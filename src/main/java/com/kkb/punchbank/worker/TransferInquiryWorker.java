package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.transfer.response.TransfersGetResDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface TransferInquiryWorker {
	TransfersGetResDto getTransfers(@NotBlank String accountNumber, long userId);
}
