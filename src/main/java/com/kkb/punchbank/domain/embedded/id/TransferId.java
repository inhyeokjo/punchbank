package com.kkb.punchbank.domain.embedded.id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransferId implements Serializable {

	private String accountNumber;
	@Column(columnDefinition = "char")
	private String transferId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TransferId that = (TransferId) o;
		return Objects.equals(accountNumber, that.accountNumber)
				&& Objects.equals(transferId, that.transferId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, transferId);
	}
}
