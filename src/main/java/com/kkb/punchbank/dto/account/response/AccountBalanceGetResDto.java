package com.kkb.punchbank.dto.account.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalanceGetResDto {
	private final BigDecimal balance;

	public static AccountBalanceGetResDto of(BigDecimal balance) {
		return new AccountBalanceGetResDto(balance);
	}
}
