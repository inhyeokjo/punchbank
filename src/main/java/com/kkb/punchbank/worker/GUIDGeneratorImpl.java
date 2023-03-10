package com.kkb.punchbank.worker;

import com.kkb.punchbank.global.constant.SystemConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * GUIDGenerator는 요청한 하나 생성되는 객체이다.
 * transactionGuid는 생성시에 초기화된다.
 */
@Component
@RequestScope
public class GUIDGeneratorImpl implements GUIDGenerator {
	private String guid;
	private static final AtomicLong wireTransferPostFix = new AtomicLong(0L);
	private static final AtomicLong transactionHistoryPostFix = new AtomicLong(0L);
	private static final AtomicLong transferPostFix = new AtomicLong(0L);

	@PostConstruct
	protected void init() {
		guid = generateGuid();
	}

	@Override
	public String getGuid() {
		return guid;
	}

	@Override
	public String generateWireTransferId(String forwardBankCode) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getFormattedDateTime());
		stringBuilder.append(SystemConstant.MY_BANK_CODE);
		stringBuilder.append(forwardBankCode);
		stringBuilder.append(getNextValue(wireTransferPostFix));
		return stringBuilder.toString();
	}

	@Override
	public String generateTransferId() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getFormattedDateTime());
		stringBuilder.append(getNextValue(transferPostFix));
		return stringBuilder.toString();
	}

	private String generateGuid() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getFormattedDateTime());
		stringBuilder.append(getNextValue(transactionHistoryPostFix));
		return stringBuilder.toString();
	}

	private String getNextValue(AtomicLong atomicValue) {
		long atomicValueValue = atomicValue.getAndUpdate(operand -> (operand + 1) % 100000);
		return String.format("%05d", atomicValueValue);
	}

	private String getFormattedDateTime() {
		return LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
}
