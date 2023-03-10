package com.kkb.punchbank.worker;

import com.kkb.punchbank.service.AccountService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountInquiryWorkerImplTest {

	@InjectMocks
	AccountInquiryWorkerImpl accountInquiryWorker;
	@Mock
	AccountService accountService;
}