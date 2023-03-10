package com.kkb.punchbank.domain.embedded.id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHistoryId implements Serializable {
	private static final long serialVersionUID = -4233113764571010778L;
	@Column(columnDefinition = "char")
	private String transactionDate;
	@Column(columnDefinition = "char")
	private String transactionHistoryId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TransactionHistoryId that = (TransactionHistoryId) o;
		return Objects.equals(transactionDate, that.transactionDate) && Objects.equals(transactionHistoryId,
				that.transactionHistoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionDate, transactionHistoryId);
	}
}
