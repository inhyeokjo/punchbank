package com.kkb.punchbank.global.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAdvice {

	ThreadLocal<Integer> indent = ThreadLocal.withInitial(() -> 0);
	@Pointcut("execution(* com.kkb.punchbank.repository.*..*(..))")
	private void allRepository() {}
	@Pointcut("execution(* com.kkb.punchbank.global.*..*(..))")
	private void allGlobal() {}
	@Pointcut("execution(* com.kkb.punchbank.*..*(..))")
	private void all() {}
	@Around("all() && !allRepository() && !allGlobal() && !execution(* com.kkb.punchbank.worker.GUIDGenerator.*(..))")
	private Object logAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().toShortString();
		List<Object> argList = Arrays.asList(joinPoint.getArgs());

		Integer requestIndent = indent.get();
		log.info("|{}--> {}|Args={}", " |".repeat(requestIndent), methodName, argList);
		indent.set(requestIndent + 1);

		Object proceed = joinPoint.proceed();

		log.info("|{}<-- {}|Args={}", " |".repeat(requestIndent), methodName, argList);
		indent.set(requestIndent);

		return proceed;
	}
}
