package com.kkb.punchbank.global.exception.forbidden;

import com.kkb.punchbank.global.exception.BaseCustomException;

public class ForbiddenException extends BaseCustomException {
	public ForbiddenException(String msg) {
		super(msg);
	}
}
