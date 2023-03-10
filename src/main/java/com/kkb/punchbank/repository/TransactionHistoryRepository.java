package com.kkb.punchbank.repository;

import com.kkb.punchbank.domain.PbTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<PbTransactionHistory, String> {
}