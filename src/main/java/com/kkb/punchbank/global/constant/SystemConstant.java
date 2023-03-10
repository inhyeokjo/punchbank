package com.kkb.punchbank.global.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemConstant {
	public static final String MY_BANK_CODE = "012";
	public static final int MAX_GUID_POST_FIX = 100000;
	public static final String WIRE_DEPOSIT_CALLBACK_URL = "http://ad-its-app15.kbin.io:8080/api/wire/deposit/result/info";
}
