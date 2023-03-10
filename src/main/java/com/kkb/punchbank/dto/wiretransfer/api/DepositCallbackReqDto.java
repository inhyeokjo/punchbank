package com.kkb.punchbank.dto.wiretransfer.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepositCallbackReqDto {
	private final String transactionId;
	private final boolean success;
	private final String message;
	private final String reqTime;

	public static DepositCallbackReqDto successDtoOf(String wireTransferId) {
		return new DepositCallbackReqDto(wireTransferId, true, "ok", LocalDateTime.now().toString());
	}

	public static DepositCallbackReqDto failDtoOf(String wireTransferId, String message) {
		return new DepositCallbackReqDto(wireTransferId, false, message, LocalDateTime.now().toString());
	}
}
