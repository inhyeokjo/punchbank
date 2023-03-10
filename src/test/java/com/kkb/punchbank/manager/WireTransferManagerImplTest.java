package com.kkb.punchbank.manager;

import com.kkb.punchbank.worker.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WireTransferManagerImplTest {

	@InjectMocks
	WireTransferManagerImpl wireTransferManager;
	@Mock
	WithdrawalWorker withdrawalWorker;
	@Mock
	DepositWorker depositWorker;
	@Mock
	OtherBankDepositWorker otherBankDepositWorker;
	@Mock
	GUIDGenerator guidGenerator;
	@Mock
	TransferWorker transferWorker;
}