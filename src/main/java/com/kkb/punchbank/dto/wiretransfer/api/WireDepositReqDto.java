package com.kkb.punchbank.dto.wiretransfer.api;

import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.global.constant.SystemConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class WireDepositReqDto {
	private WireAccountDto withdraw;
	private WireAccountDto deposit;
	private BigDecimal amount;
	private LocalDateTime reqTime;
	private String callbackUrl;
	private String transactionId;

	public static WireDepositReqDto from(WireTransferReqDto dto, String wireTransferId) {
		return WireDepositReqDto.builder()
				.withdraw(new WireAccountDto(
						dto.getWithdrawal().getBankCode(),
						dto.getWithdrawal().getAccountNumber(),
						dto.getWithdrawal().getMessage(),
						dto.getWithdrawal().getAccountHolder()))
				.deposit(new WireAccountDto((
						dto.getDeposit().getBankCode()),
						dto.getDeposit().getAccountNumber(),
						dto.getDeposit().getMessage(),
						dto.getDeposit().getAccountHolder()))
				.amount(dto.getAmount())
				.reqTime(LocalDateTime.now())
				.callbackUrl(SystemConstant.WIRE_DEPOSIT_CALLBACK_URL)
				.transactionId(wireTransferId)
				.build();
	}

	@Data
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class WireAccountDto {
		private String bankId;
		private String accountNumber;
		private String message;
		private String username;
	}
}
