package com.kkb.punchbank.worker;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(MockitoExtension.class)
class GUIDGeneratorImplTest {

	public GUIDGeneratorImpl guidGeneratorImpl = new GUIDGeneratorImpl();

	@Test
	@DisplayName("TransferId 생성 동시성 테스트")
	void transferIdGenerateTest() throws InterruptedException {
		int threadCount = 1000000;
		int threadPoolSize = 32;

		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
		CountDownLatch latch = new CountDownLatch(threadCount);

		List<String> list = Collections.synchronizedList(new ArrayList<>());

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					String historyId = guidGeneratorImpl.generateTransferId();
					list.add(historyId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Set<String> hIdSet = new HashSet<>(list);
		Assertions.assertThat(hIdSet.size()).isEqualTo(list.size());
	}

	@Test
	@DisplayName("WireTransferId 생성 동시성 테스트")
	void wireTransferIdGenerateTest() throws InterruptedException {
		int threadCount = 1000000;
		int threadPoolSize = 64;

		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
		CountDownLatch latch = new CountDownLatch(threadCount);

		List<String> list = Collections.synchronizedList(new ArrayList<>());

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					String historyId = guidGeneratorImpl.generateWireTransferId("012");
					list.add(historyId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Set<String> hIdSet = new HashSet<>(list);
		Assertions.assertThat(hIdSet.size()).isEqualTo(list.size());
	}
}