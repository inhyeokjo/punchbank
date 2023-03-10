package com.kkb.punchbank.domain;

import com.kkb.punchbank.domain.embedded.id.TransactionHistoryId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PbTransactionHistory {
	@EmbeddedId
	private TransactionHistoryId transactionHistoryId;
	@Column(columnDefinition = "char")
	private String transactionTime;
	private String uri;
	private String method;
	@Column(columnDefinition = "text")
	private String request;
	@Column(columnDefinition = "text")
	private String response;
	private long requestUser;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void setUserId(long userId) {
		this.requestUser = userId;
	}

	public void setRequest(String requestContent) {
		this.request = requestContent;
	}
}
