package com.kkb.punchbank.dto.account.entity;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.enums.AccountState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDto implements Serializable {
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;
	private final String accountNumber;
	private final BigDecimal balance;
	private final long ownerId;
	private final AccountState state;
	private final String ownerName;

	public static AccountDto of(PbAccount account) {
		return new AccountDto(
				account.getCreatedAt(),
				account.getModifiedAt(),
				account.getAccountNumber(),
				account.getBalance(),
				account.getOwner().getUserId(),
				account.getState(),
				account.getOwnerName());
	}
}