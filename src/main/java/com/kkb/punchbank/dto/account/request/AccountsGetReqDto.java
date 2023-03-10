package com.kkb.punchbank.dto.account.request;

import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.validator.Duration;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AccountsGetReqDto {
	@NotNull(message = ExceptionMessage.USER_ID_NULL)
	private final Long userId;
	@NotNull(message = ExceptionMessage.REQUEST_TIME_NULL)
	@Duration(beforeSecond = 10, message=ExceptionMessage.REQUEST_NOT_IN_TIME)
	private final LocalDateTime reqTime;
}
