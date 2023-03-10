package com.kkb.punchbank.global.exception.unauthorized;

import com.kkb.punchbank.global.exception.BaseCustomException;

public class UnauthorizedException extends BaseCustomException {
	protected UnauthorizedException(String msg) {
		super(msg);
	}
}
