package com.kkb.punchbank.dto.wiretransfer.request;

import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.validator.Duration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WireTransferDepositReqDto {
	@NotNull(message = ExceptionMessage.WITHDRAWAL_ACCOUNT_INFO_NULL)
	private final WireAccountDto withdraw;
	@NotNull(message = ExceptionMessage.DEPOSIT_ACCOUNT_INFO_NULL)
	private final WireAccountDto deposit;
	private final BigDecimal amount;
	@Duration(beforeSecond = 10, message=ExceptionMessage.REQUEST_NOT_IN_TIME)
	@NotNull(message = ExceptionMessage.REQUEST_TIME_NULL)
	private final LocalDateTime reqTime;
	@NotBlank(message = ExceptionMessage.CALLBACK_URL_BLANK)
	private final String callbackUrl;
	@NotBlank(message = ExceptionMessage.BLANK_TRANSFER_ID)
	private final String transactionId;

	@Data
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class WireAccountDto {
		@NotBlank(message = ExceptionMessage.BANK_CODE_EMPTY)
		@Size(min = 3, max = 3, message = ExceptionMessage.BANK_CODE_NOT_THREE)
		private final String bankId;
		@NotBlank(message = ExceptionMessage.ACCOUNT_NUMBER_EMPTY)
		private final String accountNumber;
		private final String message;
		@NotBlank(message = ExceptionMessage.ACCOUNT_HOLDER_EMPTY)
		private final String username;
	}
}
