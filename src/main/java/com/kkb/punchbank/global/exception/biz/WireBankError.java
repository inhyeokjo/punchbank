package com.kkb.punchbank.global.exception.biz;

public class WireBankError extends BizException {
	public WireBankError(String msg) {
		super(msg);
	}

	public WireBankError(String message, Throwable cause) {
		super(message, cause);
	}
}
