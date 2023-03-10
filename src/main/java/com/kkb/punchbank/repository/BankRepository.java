package com.kkb.punchbank.repository;

import com.kkb.punchbank.domain.PbBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankRepository extends JpaRepository<PbBank, String> {
	@Query("SELECT b.transferUrl FROM PbBank b WHERE b.bankCode = :bankCode")
	Optional<String> findTransferUrlFromBankCode(@Param("bankCode") String bankCode);
}