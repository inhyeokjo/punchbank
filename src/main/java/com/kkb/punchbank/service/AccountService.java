package com.kkb.punchbank.service;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.badrequest.NoAccountException;
import com.kkb.punchbank.global.exception.forbidden.ForbiddenException;
import com.kkb.punchbank.repository.AccountRepository;
import com.kkb.punchbank.service.pojo.WithdrawCheckDto;
import com.kkb.punchbank.service.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;
	private final AccountValidator accountValidator;

	//단순 읽기 작업이라 Transaction이 필요 없음.
	public List<PbAccount> getAccounts(long userId) {
		return accountRepository.getActiveAccountListByOwnerId(userId);
	}

	//계좌를 조회해 오는 쿼리가 단 한번만 나가며, 읽기 작업만 수행하기 때문에 Transaction이 필요없음.
	public BigDecimal getAccountBalance(String accountNumber) {
		return getAccount(accountNumber).getBalance();
	}

	//단순 읽기 작업이라 Transaction사용하지 않음
	public String getAccountHolderName(String accountNumber) {
		return getAccount(accountNumber).getOwnerName();
	}

	public PbAccount getAccount(String accountNumber) {
		return accountRepository.getAccountByAccountNumber(accountNumber)
				.orElseThrow(() -> new NoAccountException(ExceptionMessage.WRONG_ACCOUNT));
	}

	public PbAccount getAccountWithLock(String accountNumber) {
		return accountRepository.getAccountByAccountNumberWithLock(accountNumber)
				.orElseThrow(() -> new NoAccountException(ExceptionMessage.WRONG_ACCOUNT));
	}

	public long getAccountHolderId(String accountNumber) {
		return getAccount(accountNumber).getOwner().getUserId();
	}

	public void checkAccountInquiry(String accountNumber, long userId) {
		if (userId != getAccountHolderId(accountNumber)) {
			throw new ForbiddenException(ExceptionMessage.ACCOUNT_FORBIDDEN);
		}
	}

	public void checkDeposit(PbAccount account, String holderName) {
		accountValidator.accountActiveCheck(account);
		accountValidator.accountHolderCheck(account, holderName);
	}

	public void checkWithdraw(PbAccount account, WithdrawCheckDto withdrawCheckDto) {
		accountValidator.accountActiveCheck(account);
		accountValidator.balanceCheck(account, withdrawCheckDto.getAmount());
		accountValidator.accountHolderCheck(account, withdrawCheckDto.getHolderName());
	}
}
