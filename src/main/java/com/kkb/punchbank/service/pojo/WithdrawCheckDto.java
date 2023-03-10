package com.kkb.punchbank.service.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawCheckDto {
	private final BigDecimal amount;
	private final String holderName;
}
