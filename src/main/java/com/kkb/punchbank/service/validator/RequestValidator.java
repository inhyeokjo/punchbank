package com.kkb.punchbank.service.validator;

import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.constant.SystemConstant;
import com.kkb.punchbank.global.exception.SameAccountTransferException;
import com.kkb.punchbank.global.exception.badrequest.NotWireTransferException;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

	public void checkWireTransfer(WireTransferReqDto requestDto) {
		sameAccountCheck(requestDto);
		wireTransferCheck(requestDto);
	}

	private static void sameAccountCheck(WireTransferReqDto requestDto) {
		if (requestDto.getWithdrawal().getAccountNumber().equals(requestDto.getDeposit().getAccountNumber())) {
			throw new SameAccountTransferException(ExceptionMessage.SAME_ACCOUNT_TRANSFER);
		}
	}

	private static void wireTransferCheck(WireTransferReqDto requestDto) {
		if (requestDto.getDeposit().getBankCode().equals(SystemConstant.MY_BANK_CODE)) {
			throw new NotWireTransferException(ExceptionMessage.NOT_WIRE_TRANSFER);
		}
	}
}
