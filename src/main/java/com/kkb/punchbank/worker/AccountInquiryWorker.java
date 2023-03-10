package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.account.response.AccountBalanceGetResDto;
import com.kkb.punchbank.dto.account.response.AccountsGetResDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface AccountInquiryWorker {
	AccountBalanceGetResDto getAccountBalance(@NotBlank String accountNumber, long userId);
	AccountsGetResDto getAccounts(long userId);
}
