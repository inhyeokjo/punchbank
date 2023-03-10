package com.kkb.punchbank.dto.transfer.response;

import com.kkb.punchbank.domain.PbTransfer;
import com.kkb.punchbank.domain.enums.TransferType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransfersGetResDto {
	List<TransferDto> transferDtoList;

	public static TransfersGetResDto fromTransferList(List<PbTransfer> transferDtoList) {
		return new TransfersGetResDto(transferDtoList.stream()
				.map(TransferDto::from)
				.collect(Collectors.toList()));
	}

	@Data
	@Builder(access = AccessLevel.PRIVATE)
	public static class TransferDto {

		private String displayContents;
		private BigDecimal amount;
		private LocalDateTime date;
		private BigDecimal balance;
		private TransferType transferType;
		private String transferProgress;

		public static TransferDto from(PbTransfer transfer) {
			return TransferDto.builder()
					.amount(transfer.getAmount())
					.displayContents(transfer.getMessage())
					.date(transfer.getCreatedAt())
					.balance(transfer.getBalance())
					.transferType(transfer.getTransferType())
					.transferProgress(transfer.getTransferProgress().toDisplayString())
					.build();
		}
	}
}
