package com.kkb.punchbank.controller;

import com.kkb.punchbank.dto.account.request.AccountBalanceGetReqDto;
import com.kkb.punchbank.dto.account.request.AccountsGetReqDto;
import com.kkb.punchbank.dto.account.response.AccountBalanceGetResDto;
import com.kkb.punchbank.dto.account.response.AccountsGetResDto;
import com.kkb.punchbank.worker.AccountInquiryWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
	private final AccountInquiryWorker accountInquiryWorker;

	@PostMapping("/accounts/get")
	public AccountsGetResDto getAccounts(@RequestBody @Valid AccountsGetReqDto requestDto) {
		return accountInquiryWorker.getAccounts(requestDto.getUserId());
	}

	@PostMapping("/account/balance/get")
	public AccountBalanceGetResDto getAccountBalance(@RequestBody @Valid AccountBalanceGetReqDto requestDto) {
		String accountNumber = requestDto.getAccountNumber();
		long userId = requestDto.getUserId();

		return accountInquiryWorker.getAccountBalance(accountNumber, userId);
	}
}
