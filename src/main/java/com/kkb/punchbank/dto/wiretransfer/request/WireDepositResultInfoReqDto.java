package com.kkb.punchbank.dto.wiretransfer.request;

import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.validator.Duration;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class WireDepositResultInfoReqDto {
	@NotBlank(message = ExceptionMessage.BLANK_TRANSFER_ID)
	private String transactionId;
	private String message;
	@Duration(beforeSecond = 10, message=ExceptionMessage.REQUEST_NOT_IN_TIME)
	@NotNull(message = ExceptionMessage.REQUEST_TIME_NULL)
	private LocalDateTime reqTime;

	@NotNull(message = ExceptionMessage.SUCCESS_VALUE_NULL)
	private boolean success;
}
