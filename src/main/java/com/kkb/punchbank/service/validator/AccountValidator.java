package com.kkb.punchbank.service.validator;

import com.kkb.punchbank.domain.PbAccount;
import com.kkb.punchbank.domain.enums.AccountState;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.biz.InsufficientBalanceException;
import com.kkb.punchbank.global.exception.biz.NotActiveAccountException;
import com.kkb.punchbank.global.exception.forbidden.ForbiddenException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountValidator {
	public void balanceCheck(PbAccount account, BigDecimal amount) {
		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException(ExceptionMessage.INSUFFICIENT_BALANCE);
		}
	}

	public void accountHolderCheck(PbAccount account, String holderName) {
		if (!account.getOwner().getName().equals(holderName)) {
			throw new ForbiddenException(ExceptionMessage.ACCOUNT_FORBIDDEN);
		}
	}

	public void accountActiveCheck(PbAccount account) {
		if (account.getState() != AccountState.ACTIVE) {
			throw new NotActiveAccountException(ExceptionMessage.ACCOUNT_NOT_ACTIVE);
		}
	}
}
