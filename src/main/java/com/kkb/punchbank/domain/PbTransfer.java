package com.kkb.punchbank.domain;

import com.kkb.punchbank.domain.embedded.id.TransferId;
import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.domain.enums.TransferType;
import com.kkb.punchbank.dto.transfer.request.TransferReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferDepositReqDto;
import com.kkb.punchbank.dto.wiretransfer.request.WireTransferReqDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PbTransfer extends BaseEntity {
	@EmbeddedId
	private TransferId transferId;
	@Enumerated(EnumType.STRING)
	private TransferType transferType;
	@Column(columnDefinition = "char")
	private String transactionHistoryId;
	private String message;
	@Column(columnDefinition = "bigint")
	private BigDecimal amount;
	private String holder;
	private String tradingAccountNumber;
	private String tradingAccountName;
	private String tradingAccountMessage;
	@Column(columnDefinition = "char")
	private String tradingAccountBankCode;
	@Enumerated(EnumType.STRING)
	private TransferProgress transferProgress;
	@Column(columnDefinition = "bigint")
	private BigDecimal balance;
	private Integer errorCode;
	@Column(columnDefinition = "char")
	private String wireTransferId;

	public static PbTransfer withdrawalTransferFrom(TransferReqDto dto, String transactionId, String transferId) {
		return PbTransfer.builder()
				.transferId(new TransferId(dto.getWithdrawal().getAccountNumber(), transferId))
				.amount(BigDecimal.ZERO.subtract(dto.getAmount()))
				.transferType(TransferType.WITHDRAW)
				.message(dto.getWithdrawal().getMessage())
				.tradingAccountNumber(dto.getDeposit().getAccountNumber())
				.tradingAccountName(dto.getDeposit().getAccountHolder())
				.tradingAccountMessage(dto.getDeposit().getMessage())
				.tradingAccountBankCode(dto.getDeposit().getBankCode())
				.transferProgress(TransferProgress.START)
				.transactionHistoryId(transactionId)
				.build();
	}

	public static PbTransfer depositTransferFrom(TransferReqDto dto, String transactionId, String transferId) {
		return PbTransfer.builder()
				.transferId(new TransferId(dto.getDeposit().getAccountNumber(), transferId))
				.amount(dto.getAmount())
				.transferType(TransferType.DEPOSIT)
				.message(dto.getDeposit().getMessage())
				.tradingAccountNumber(dto.getWithdrawal().getAccountNumber())
				.tradingAccountName(dto.getDeposit().getAccountHolder())
				.tradingAccountMessage(dto.getWithdrawal().getMessage())
				.tradingAccountBankCode(dto.getWithdrawal().getBankCode())
				.transferProgress(TransferProgress.START)
				.transactionHistoryId(transactionId)
				.build();
	}

	public static PbTransfer withdrawalTransferFrom(WireTransferReqDto dto, String transactionId, String transferId, String wireTransferId) {
		return PbTransfer.builder()
				.transferId(new TransferId(dto.getWithdrawal().getAccountNumber(), transferId))
				.transferType(TransferType.WITHDRAW)
				.transactionHistoryId(transactionId)
				.message(dto.getWithdrawal().getMessage())
				.amount(BigDecimal.ZERO.subtract(dto.getAmount()))
				.holder(dto.getWithdrawal().getAccountHolder())
				.tradingAccountNumber(dto.getDeposit().getAccountNumber())
				.tradingAccountName(dto.getDeposit().getAccountHolder())
				.tradingAccountMessage(dto.getDeposit().getMessage())
				.tradingAccountBankCode(dto.getDeposit().getBankCode())
				.transferProgress(TransferProgress.START)
				.wireTransferId(wireTransferId)
				.build();
	}

	public static PbTransfer depositTransferFrom(WireTransferDepositReqDto dto, String transactionId, String transferId, String wireTransferId) {
		return PbTransfer.builder()
				.transferId(new TransferId(dto.getDeposit().getAccountNumber(), transferId))
				.transferType(TransferType.DEPOSIT)
				.message(dto.getDeposit().getMessage())
				.amount(dto.getAmount())
				.tradingAccountNumber(dto.getWithdraw().getAccountNumber())
				.tradingAccountName(dto.getWithdraw().getUsername())
				.tradingAccountMessage(dto.getWithdraw().getMessage())
				.tradingAccountBankCode(dto.getWithdraw().getBankId())
				.transferProgress(TransferProgress.START)
				.transactionHistoryId(transactionId)
				.wireTransferId(wireTransferId)
				.build();
	}

	public void setBalance(BigDecimal withdrawalAccountBalance) {
		balance = withdrawalAccountBalance;
	}

	public void setTransferProgress(TransferProgress transferProgress) {
		this.transferProgress = transferProgress;
	}

	public boolean isWithdrawSuccess() {
		return transferProgress == TransferProgress.WITHDRAW_SUCCESS;
	}
}
