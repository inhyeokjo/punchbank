package com.kkb.punchbank.worker;

import com.kkb.punchbank.dto.transfer.response.TransfersGetResDto;
import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferInquiryWorkerImpl implements TransferInquiryWorker {

	private final AccountService accountService;
	private final TransferService transferService;

	@Override
	public TransfersGetResDto getTransfers(String accountNumber, long userId) {
		accountService.checkAccountInquiry(accountNumber, userId);
		return TransfersGetResDto.fromTransferList(transferService.getTransfers(accountNumber));
	}
}
