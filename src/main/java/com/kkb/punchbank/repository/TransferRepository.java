package com.kkb.punchbank.repository;

import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.embedded.id.TransferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransferRepository extends JpaRepository<PbTransfer, TransferId> {

	@Query("SELECT t FROM PbTransfer t WHERE t.transferId.accountNumber = :accountNumber ORDER BY t.createdAt desc")
	List<PbTransfer> findAllByAccountNumber(@Param("accountNumber") String accountNumber);

	@Query("SELECT t FROM PbTransfer t WHERE t.wireTransferId = :wireTransferId")
	Optional<PbTransfer> findByWireTransferId(@Param("wireTransferId") String wireTransferId);
}