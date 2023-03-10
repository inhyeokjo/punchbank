package com.kkb.punchbank.global.aop;

import com.kkb.punchbank.global.exception.badrequest.BadRequestException;
import com.kkb.punchbank.global.exception.biz.BizException;
import com.kkb.punchbank.global.exception.forbidden.ForbiddenException;
import com.kkb.punchbank.global.exception.unauthorized.UnauthorizedException;
import com.kkb.punchbank.worker.GUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerAdvice {

	private final GUIDGenerator guidGenerator;

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	protected String baseExceptionHandler(Exception e) {
		log.error("알 수 없는 에러가 발생했습니다. \t{} : {}", e.getClass(), e.getMessage());

		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error(String.valueOf(stringBuilder));
		return String.format("서버 에러가 발생했습니다. 개발팀으로 문의 주세요. \nerror guid : %s", guidGenerator.getGuid());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(BadRequestException.class)
	protected String badRequestExceptionHandler(BadRequestException e) {
		log.error("잘못된 요청 입니다. \t{} : {}", e.getClass(), e.getMessage());
		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error("{}", stringBuilder);
		return String.format("%s \nerror guid : %s", e.getMessage(), guidGenerator.getGuid());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		log.error("잘못된 요청 입니다. \t{} : {}", e.getClass(), e.getMessage());
		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error(String.valueOf(stringBuilder));
		return String.format("%s \nerror guid : %s", e.getMessage(), guidGenerator.getGuid());
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	protected String forbiddenExceptionHandler(ForbiddenException e) {
		log.error("권한이 없습니다. \t{} : {}", e.getClass(), e.getMessage());
		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error(String.valueOf(stringBuilder));
		return String.format("%s \nerror guid : %s", e.getMessage(), guidGenerator.getGuid());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	protected String unauthorizedExceptionHandler(UnauthorizedException e) {
		log.error("인증되지 않은 회원입니다. \t{} : {}", e.getClass(), e.getMessage());
		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error(String.valueOf(stringBuilder));
		return String.format("%s \nerror guid : %s", e.getMessage(), guidGenerator.getGuid());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BizException.class)
	protected String bizExceptionHandler(BizException e) {
		log.error("비즈니스 로직 오류입니다. \t{} : {}", e.getClass(), e.getMessage());
		StringBuilder stringBuilder = new StringBuilder();
		createSystemErrorMessage(stringBuilder, e);
		log.error(String.valueOf(stringBuilder));
		return String.format("%s \nerror guid : %s", e.getMessage(), guidGenerator.getGuid());
	}


	private static void createSystemErrorMessage(StringBuilder stringBuilder, Throwable e) {
		Throwable cause = getTopCause(e);
		String errorTitle = String.format("Error Type : %s\nError Message : %s", cause.getClass(), cause.getMessage());
		stringBuilder.append(errorTitle);
		for (StackTraceElement stackTraceElement : cause.getStackTrace()) {
			stringBuilder.append("\n");
			stringBuilder.append(stackTraceElement.toString());
		}
	}

	private static Throwable getTopCause(Throwable cause) {
		while (cause.getCause() != null) {
			cause = cause.getCause();
		}
		return cause;
	}
}
