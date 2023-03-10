package com.kkb.punchbank.domain.enums;

import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.biz.TransferProgressNotDefined;

public enum TransferProgress {
	START,
	SUCCESS,
	CHECKED,
	FAIL,
	RECOVER,
	WITHDRAW_SUCCESS;
	public String toDisplayString() {
		if (this == TransferProgress.START || this == CHECKED || this == FAIL || this == WITHDRAW_SUCCESS)
			return "PROCEEDING";
		if (this == SUCCESS)
			return "SUCCESS";
		if (this == RECOVER)
			return "FAIL";
		throw new TransferProgressNotDefined(ExceptionMessage.TRANSFER_PROGRESS_NOT_DEFINED);
	}
}
