package com.kkb.punchbank.repository;

import com.kkb.punchbank.domain.PbAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<PbAccount, String> {
	@Query("SELECT a FROM PbAccount a WHERE a.owner.userId = :userId and a.state = com.kkb.punchbank.domain.enums.AccountState.ACTIVE")
	List<PbAccount> getActiveAccountListByOwnerId(@Param("userId") long ownerId);

	Optional<PbAccount> getAccountByAccountNumber(String accountNumber);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT a FROM PbAccount a WHERE a.accountNumber = :accountNumber")
	Optional<PbAccount> getAccountByAccountNumberWithLock(@Param("accountNumber") String accountNumber);
}