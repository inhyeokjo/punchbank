package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.account.response.AccountBalanceGetResDto;
import com.kkb.punchbank.dto.account.response.AccountsGetResDto;
import com.kkb.punchbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountInquiryWorkerImpl implements AccountInquiryWorker {
	private final AccountService accountService;
	@Override
	public AccountBalanceGetResDto getAccountBalance(String accountNumber, long userId) {
		accountService.checkAccountInquiry(accountNumber, userId);
		return AccountBalanceGetResDto.of(accountService.getAccountBalance(accountNumber));
	}


	@Override
	public AccountsGetResDto getAccounts(long userId) {
		return AccountsGetResDto.fromAccountList(accountService.getAccounts(userId));
	}
}
