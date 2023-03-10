package com.kkb.punchbank.worker;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
public interface GUIDGenerator {
	String getGuid();
	String generateTransferId();
	String generateWireTransferId(@Size(min = 3, max = 3) @NotBlank String forwardBankCode);
}
