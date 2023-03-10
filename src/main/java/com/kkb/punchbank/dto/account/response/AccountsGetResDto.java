package com.kkb.punchbank.dto.account.response;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.enums.AccountType;
import com.kkb.punchbank.global.constant.SystemConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class AccountsGetResDto {
	private final List<AccountDto> accounts;

	public static AccountsGetResDto fromAccountList(List<PbAccount> accountList) {
		List<AccountDto> accountDtoList = accountList.stream()
				.map(AccountDto::from)
				.collect(Collectors.toList());
		return new AccountsGetResDto(accountDtoList);
	}

	public AccountsGetResDto concat(AccountsGetResDto firstResponseDto) {
		List<AccountDto> newResponseDto = new ArrayList<>(firstResponseDto.getAccounts());
		newResponseDto.addAll(this.accounts);
		return new AccountsGetResDto(newResponseDto);
	}

	@Data
	@Builder(access = AccessLevel.PRIVATE)
	public static class AccountDto {

		private String bankCode;
		private String accountNumber;
		private BigDecimal balance;
		private AccountType type;

		public static AccountDto from(PbAccount account) {
			return AccountDto.builder()
					.accountNumber(account.getAccountNumber())
					.balance(account.getBalance())
					.bankCode(SystemConstant.MY_BANK_CODE)
					.type(account.getType())
					.build();
		}
	}
}
