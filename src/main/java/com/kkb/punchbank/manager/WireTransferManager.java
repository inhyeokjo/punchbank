package com.kkb.punchbank.manager;

import com.kkb.punchbank.dto.wiretransfer.request.WireDepositResultInfoReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.response.WireTransferResDto;

public interface WireTransferManager {
	WireTransferResDto wireTransfer(WireTransferReqDto requestDto);

	void postProcessWireTransfer(WireDepositResultInfoReqDto requestDto);

	void wireDeposit(WireTransferDepositReqDto requestDto);
}
