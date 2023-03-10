package com.kkb.punchbank.dto.wiretransfer.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WireDepositResultInfoResDto {
	private LocalDateTime resTime;
	private String message;
}
