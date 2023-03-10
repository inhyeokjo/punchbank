package com.kkb.punchbank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PbUser extends BaseEntity {
	@Id
	long userId;

	String name;
	@Column(name = "`use`")
	public boolean use;
}
