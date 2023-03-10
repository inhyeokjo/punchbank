package com.kkb.punchbank.worker;

import com.kkb.punchbank.service.BankService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OtherBankDepositWorkerImplTest {

	@InjectMocks
	OtherBankDepositWorkerImpl otherBankDepositWorker;
	@Mock
	BankService bankService;
}