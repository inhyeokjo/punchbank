package com.kkb.punchbank.dto.wiretransfer.response;

import lombok.Data;

@Data
public class WireTransferResDto {
	private final String transferId;

	public static WireTransferResDto of(String transferId) {
		return new WireTransferResDto(transferId);
	}
}
