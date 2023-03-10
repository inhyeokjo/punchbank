package com.kkb.punchbank.service;

import com.kkb.punchbank.global.exception.biz.NotServiceYet;
import com.kkb.punchbank.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

	private final BankRepository bankRepository;

	public String getTransferUrl(String bankCode) {
		return bankRepository.findTransferUrlFromBankCode(bankCode)
				.orElseThrow(() -> new NotServiceYet("해당 은행의 타행이체는 아직 지원하지 않습니다."));
	}
}
