package com.kkb.punchbank.global.exception.biz;

import com.kkb.punchbank.global.exception.BaseCustomException;

public class BizException extends BaseCustomException {
	protected BizException(String msg) {
		super(msg);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
}
