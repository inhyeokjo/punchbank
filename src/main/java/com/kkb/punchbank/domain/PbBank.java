package com.kkb.punchbank.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PbBank {
	@Id
	@Column(columnDefinition = "char")
	private String bankCode;
	private String bankName;
	private String transferUrl;
}
