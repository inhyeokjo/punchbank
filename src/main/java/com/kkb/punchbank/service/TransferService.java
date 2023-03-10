package com.kkb.punchbank.service;

import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.biz.NoWireTransferRecord;
import com.kkb.punchbank.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

	private final TransferRepository transferRepository;

	public List<PbTransfer> getTransfers(String accountNumber) {
		return transferRepository.findAllByAccountNumber(accountNumber);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void changeTransferProgress(PbTransfer transfer, TransferProgress transferProgress) {
		log.info("set transferProgress to {} (transferId = {})", transferProgress, transfer.getTransferId().getTransferId());
		transfer.setTransferProgress(transferProgress);
		saveTransfer(transfer);
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void changeTransferProgress(String wireTransferId, TransferProgress transferProgress) {
		log.info("set transferProgress to {} (wireTransferId = {})", transferProgress, wireTransferId);
		PbTransfer transfer = findByWireTransferId(wireTransferId);
		transfer.setTransferProgress(transferProgress);
		saveTransfer(transfer);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTransfer(PbTransfer transfer) {
		log.info("start NEW transaction for save transfer");
		transferRepository.save(transfer);
		log.info("end NEW transaction for save transfer");
	}

	public PbTransfer findByWireTransferId(String wireTransferId) {
		return transferRepository.findByWireTransferId(wireTransferId)
				.orElseThrow(() -> new NoWireTransferRecord(ExceptionMessage.WIRE_TRANSFER_ID_WRONG));
	}
}
