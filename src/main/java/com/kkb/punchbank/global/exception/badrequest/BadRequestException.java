package com.kkb.punchbank.global.exception.badrequest;

import com.kkb.punchbank.global.exception.BaseCustomException;

public class BadRequestException extends BaseCustomException {
	protected BadRequestException(String msg) {
		super(msg);
	}
}
