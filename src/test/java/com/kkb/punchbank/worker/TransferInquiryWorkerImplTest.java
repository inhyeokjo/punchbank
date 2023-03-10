package com.kkb.punchbank.worker;

import com.kkb.punchbank.service.AccountService;
import com.kkb.punchbank.service.TransferService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferInquiryWorkerImplTest {
	@InjectMocks
	TransferInquiryWorkerImpl transferInquiryWorker;
	@Mock
	AccountService accountService;
	@Mock
	TransferService transferService;

}