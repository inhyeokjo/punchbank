package com.kkb.punchbank.worker;

import com.kkb.punchbank.domain.enums.TransferProgress;
import com.kkb.punchbank.dto.transfer.request.TransferCheckReqDto;
import com.kkb.punchbank.dto.transfer.request.TransferReqDto;
import com.kkb.punchbank.dto.transfer.response.TransferResDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface TransferWorker {
	TransferResDto transfer(TransferReqDto requestDto);
	void checkTransfer(TransferCheckReqDto requestDto);
	void changeTransferProgress(@NotBlank String wireTransferId, TransferProgress transferProgress);
}
