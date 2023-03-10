package com.kkb.punchbank.domain;

import com.kkb.punchbank.domain.enums.AccountState;
import com.kkb.punchbank.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PbAccount extends BaseEntity {

	@Id
	private String accountNumber;
	@Column(columnDefinition = "bigint")
	private BigDecimal balance;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PbUser owner;
	@Enumerated(EnumType.STRING)
	private AccountState state;
	private String ownerName;
	@Enumerated(EnumType.STRING)
	private AccountType type;

	public BigDecimal minusAmount(BigDecimal amount) {
		balance = balance.subtract(amount);
		return balance;
	}

	public BigDecimal plusAmount(BigDecimal amount) {
		balance = balance.add(amount);
		return balance;
	}
}
