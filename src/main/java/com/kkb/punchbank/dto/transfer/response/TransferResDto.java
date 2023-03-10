package com.kkb.punchbank.dto.transfer.response;

import lombok.Data;

@Data
public class TransferResDto {
	private final String transferId;

	public static TransferResDto of(String transferId) {
		return new TransferResDto(transferId);
	}
}
