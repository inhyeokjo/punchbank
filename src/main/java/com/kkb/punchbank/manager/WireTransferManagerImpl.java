package com.kkb.punchbank.manager;

import com.kkb.punchbank.dto.wiretransfer.request.WireDepositResultInfoReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.response.WireTransferResDto;
import com.kkb.punchbank.worker.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WireTransferManagerImpl implements WireTransferManager{
	private final WithdrawalWorker withdrawalWorker;
	private final DepositWorker depositWorker;
	private final OtherBankDepositWorker otherBankDepositWorker;
	private final GUIDGenerator guidGenerator;
	private final TransferWorker transferWorker;

	@Override
	public WireTransferResDto wireTransfer(WireTransferReqDto requestDto) {
		String wireTransferId = guidGenerator.generateWireTransferId(requestDto.getDeposit().getBankCode());
		WireTransferResDto wireTransferResDto = withdrawalWorker.withdraw(requestDto, wireTransferId);
		otherBankDepositWorker.otherBankDeposit(requestDto, wireTransferId, withdrawalWorker.getRecoverStrategy(wireTransferId));
		return wireTransferResDto;
	}

	@Override
	public void postProcessWireTransfer(WireDepositResultInfoReqDto requestDto) {
		String wireTransferId = requestDto.getTransactionId();
		if (requestDto.isSuccess()) {
			withdrawalWorker.successWithdraw(wireTransferId);
			log.info("this wireTransfer ended successfully (wireTransferId={}) ", requestDto.getTransactionId());
		} else {
			log.info("this wireTransfer failed (wireTransferId={}) ", requestDto.getTransactionId());
			withdrawalWorker.recoverWithdraw(wireTransferId);
			log.info("this wireTransfer recovered successfully (wireTransferId={}) ", requestDto.getTransactionId());
		}
	}

	@Override
	public void wireDeposit(WireTransferDepositReqDto requestDto) {
		String transferId = guidGenerator.generateTransferId();
		try {
			depositWorker.deposit(requestDto, transferId);
		} catch (Exception e) {
			log.info("The deposit requested by another bank has failed. (wireTransferId={})", requestDto.getTransactionId());
			depositWorker.informDepositFail(requestDto.getCallbackUrl(), requestDto.getTransactionId(), e.getMessage());
			throw e;
		}
		log.info("The deposit requested by another bank has success. (wireTransferId={})", requestDto.getTransactionId());
		depositWorker.informDepositSuccess(requestDto.getCallbackUrl(), requestDto.getTransactionId());
	}
}
