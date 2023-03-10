package com.kkb.punchbank.global.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkb.punchbank.domain.PbTransactionHistory;
import com.kkb.punchbank.domain.embedded.id.TransactionHistoryId;
import com.kkb.punchbank.service.TransactionHistoryService;
import com.kkb.punchbank.worker.GUIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionHistoryFilter implements Filter {
	private final GUIDGenerator guidGenerator;

	private final TransactionHistoryService transactionHistoryService;

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
			IOException, ServletException {
		ContentCachingRequestWrapper wreq = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper wres = new ContentCachingResponseWrapper((HttpServletResponse) response);
		log.info("request: {} {}", wreq.getMethod(), wreq.getRequestURI());

		PbTransactionHistory transactionHistory = getTransactionHistory(wreq);
		log.info("transactionHistory 최초 생성");
		transactionHistoryService.save(transactionHistory);

		chain.doFilter(wreq, wres);
		log.info("response: {} {} {}", wreq.getMethod(), wreq.getRequestURI(), wres.getStatus());

		transactionHistory.setEndTime(LocalDateTime.now());
		transactionHistory.setResponse(getResponseContent(wres));
		transactionHistory.setRequest(getRequestContent(wreq));

		String reqBody = new String(wreq.getContentAsByteArray());
		JsonNode jsonNode = new ObjectMapper().readTree(reqBody);
		Long userId;
		if (jsonNode.has("userId")) {
			userId = jsonNode.get("userId").asLong();
			transactionHistory.setUserId(userId);
		}
		log.info("transactionHistory 최종 수정");
		transactionHistoryService.save(transactionHistory);
	}

	private String getRequestContent(ContentCachingRequestWrapper wreq) {
		String header = getRequestHeader(wreq);
		String body = new String(wreq.getContentAsByteArray());
		return getContent(header, body);
	}

	private static String getRequestHeader(ContentCachingRequestWrapper wreq) {
		StringBuilder stringBuilder = new StringBuilder();
		wreq.getHeaderNames().asIterator().forEachRemaining(headerName -> stringBuilder.append(
				String.format("%s : %s \n", headerName, wreq.getHeader(headerName))));
		return stringBuilder.toString();
	}

	private String getResponseContent(ContentCachingResponseWrapper wres) throws IOException {
		String body = new String(wres.getContentAsByteArray());
		wres.copyBodyToResponse();
		String header = wres.getHeaderNames().stream()
				.map(headerName -> String.format("%s : %s \n", headerName, wres.getHeader(headerName)))
				.collect(Collectors.joining());
		return getContent(wres.getStatus(), header, body);
	}

	private PbTransactionHistory getTransactionHistory(ContentCachingRequestWrapper httpServletRequest) {

		LocalDateTime now = LocalDateTime.now();
		String transactionDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String transactionId = guidGenerator.getGuid();
		TransactionHistoryId transactionHistoryId = new TransactionHistoryId(transactionDate, transactionId);

		String transactionTime = now.format(DateTimeFormatter.ofPattern("HHmmssSSSSSS"));
		return PbTransactionHistory.builder()
				.transactionHistoryId(transactionHistoryId)
				.transactionTime(transactionTime)
				.uri(httpServletRequest.getRequestURI())
				.method(httpServletRequest.getMethod())
				.startTime(now)
				.build();
	}

	private String getContent(String header, String body) {
		return String.format("header:\n%s \n body:\n%s", header, body);
	}

	private String getContent(int status, String header, String body) {
		return String.format("status:\n%d \n header:\n%s \n body:\n%s", status, header, body);
	}
}
