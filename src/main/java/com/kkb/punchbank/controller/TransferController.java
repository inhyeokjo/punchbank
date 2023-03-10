package com.kkb.punchbank.controller;

import com.kkb.punchbank.dto.transfer.request.TransferCheckReqDto;
import com.kkb.punchbank.dto.transfer.request.TransferReqDto;
import com.kkb.punchbank.dto.transfer.request.TransfersGetReqDto;
import com.kkb.punchbank.dto.transfer.response.TransferResDto;
import com.kkb.punchbank.dto.transfer.response.TransfersGetResDto;
import com.kkb.punchbank.worker.TransferInquiryWorker;
import com.kkb.punchbank.worker.TransferWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransferController {

	private final TransferWorker transferWorker;
	private final TransferInquiryWorker transferInquiryWorker;

	@PostMapping("/transfers/get")
	public TransfersGetResDto getTransferList(@RequestBody @Valid TransfersGetReqDto requestDto) {
		String accountNumber = requestDto.getAccountNumber();
		long userId = requestDto.getUserId();

		return transferInquiryWorker.getTransfers(accountNumber, userId);
	}

	@PostMapping("/transfer/check")
	public void checkTransfer(@RequestBody @Valid TransferCheckReqDto requestDto) {
		transferWorker.checkTransfer(requestDto);
	}

	@PostMapping("/transfer")
	public TransferResDto transfer(@RequestBody @Valid TransferReqDto requestDto) {
		return transferWorker.transfer(requestDto);
	}
}
