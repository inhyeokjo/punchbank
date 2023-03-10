package com.kkb.punchbank.dto.transfer.request;

import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.validator.Duration;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferCheckReqDto {

	@NotNull(message = ExceptionMessage.USER_ID_NULL)
	private final Long userId;
	@NotNull(message = ExceptionMessage.ACCOUNT_EMPTY)
	private final AccountDto deposit;
	@NotNull(message = ExceptionMessage.ACCOUNT_EMPTY)
	private final AccountDto withdrawal;
	@NotNull(message = ExceptionMessage.AMOUNT_NULL)
	private final BigDecimal amount;
	@Duration(beforeSecond = 10, message=ExceptionMessage.REQUEST_NOT_IN_TIME)
	@NotNull(message = ExceptionMessage.REQUEST_TIME_NULL)
	private final LocalDateTime reqTime;

	@Data
	@Builder(access = AccessLevel.PRIVATE)
	public static class AccountDto {

		@NotBlank(message = ExceptionMessage.BANK_CODE_EMPTY)
		@Size(min = 3, max = 3, message = ExceptionMessage.BANK_CODE_NOT_THREE)
		private final String bankCode;
		@NotBlank(message = ExceptionMessage.ACCOUNT_NUMBER_EMPTY)
		private final String accountNumber;
		@NotBlank(message = ExceptionMessage.ACCOUNT_HOLDER_EMPTY)
		private final String accountHolder;
	}
	public static TransferCheckReqDto of(TransferReqDto transferRequestDto) {
		return new TransferCheckReqDto(
				transferRequestDto.getUserId(),
				AccountDto.builder()
						.bankCode(transferRequestDto.getDeposit().getBankCode())
						.accountNumber(transferRequestDto.getDeposit().getAccountNumber())
						.accountHolder(transferRequestDto.getWithdrawal().getAccountHolder())
						.build(),
				AccountDto.builder()
						.bankCode(transferRequestDto.getWithdrawal().getBankCode())
						.accountNumber(transferRequestDto.getWithdrawal().getAccountNumber())
						.accountHolder(transferRequestDto.getWithdrawal().getAccountHolder())
						.build(),
				transferRequestDto.getAmount(),
				LocalDateTime.now());
	}
}
