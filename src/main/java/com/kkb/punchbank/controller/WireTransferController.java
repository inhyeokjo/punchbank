package com.kkb.punchbank.controller;

import com.kkb.punchbank.dto.wiretransfer.request.WireDepositResultInfoReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.response.WireTransferResDto;
import com.kkb.punchbank.manager.WireTransferManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WireTransferController {

	private final WireTransferManager wireTransferManager;

	@PostMapping("/wire/transfer")
	public WireTransferResDto wireTransfer(@RequestBody @Valid WireTransferReqDto requestDto) {
		return wireTransferManager.wireTransfer(requestDto);
	}

	@PostMapping("/wire/deposit/result/info")
	public void wireDepositResultInfo(@RequestBody @Valid WireDepositResultInfoReqDto requestDto, ContentCachingResponseWrapper servletResponse) throws IOException {
		closeSessionFromWrapper(servletResponse);
		wireTransferManager.postProcessWireTransfer(requestDto);
	}

	@PostMapping("/wire/transfer/deposit")
	public void wireTransferDeposit(@RequestBody @Valid WireTransferDepositReqDto requestDto, ContentCachingResponseWrapper servletResponse) throws IOException {
		closeSessionFromWrapper(servletResponse);
		wireTransferManager.wireDeposit(requestDto);
	}

	private void closeSessionFromWrapper(ContentCachingResponseWrapper servletResponse) throws IOException {
		ServletResponse response = servletResponse.getResponse();
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.flush();
		outputStream.close();
	}
}
