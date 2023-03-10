package com.kkb.punchbank.worker;

import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import com.kkb.punchbank.service.validator.RequestValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(MockitoExtension.class)
class WithdrawalWorkerImplTest {
	@InjectMocks
	WithdrawalWorkerImpl withdrawalWorker;
	@Mock
	GUIDGenerator guidGenerator;
	@Mock
	AccountService accountService;
	@Mock
	TransferService transferService;
	@Mock
	RequestValidator requestValidator;
	@Mock
	PlatformTransactionManager transactionManager;

}