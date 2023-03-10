package com.kkb.punchbank.service.pojo;

import lombok.Data;

@Data
public class WithdrawRecoverDto {
	private final String withdrawalAccountNumber;
	private final String wireTransferId;

}
