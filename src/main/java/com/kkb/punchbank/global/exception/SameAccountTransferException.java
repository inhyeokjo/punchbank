package com.kkb.punchbank.global.exception;

import com.kkb.punchbank.global.exception.badrequest.BadRequestException;

public class SameAccountTransferException extends BadRequestException {
	public SameAccountTransferException(String msg) {
		super(msg);
	}
}
