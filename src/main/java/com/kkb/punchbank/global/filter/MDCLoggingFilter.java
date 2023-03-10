package com.kkb.punchbank.global.filter;


import com.kkb.punchbank.worker.GUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(0)
@RequiredArgsConstructor
public class MDCLoggingFilter implements Filter {

	private final GUIDGenerator guidGenerator;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		MDC.put("guid", guidGenerator.getGuid());
		chain.doFilter(request, response);
		MDC.clear();
	}
}
