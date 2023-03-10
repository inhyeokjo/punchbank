package com.kkb.punchbank.service;

import com.kkb.punchbank.domain.PbTransactionHistory;
import com.kkb.punchbank.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionHistoryService {

	private final TransactionHistoryRepository transactionHistoryRepository;

	public void save(PbTransactionHistory transactionHistory) {
		log.info("transactionHistory 저장");
		transactionHistoryRepository.save(transactionHistory);
	}
}
