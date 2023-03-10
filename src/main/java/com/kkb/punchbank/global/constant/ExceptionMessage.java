package com.kkb.punchbank.global.constant;

public class ExceptionMessage {

	public static final String USER_ID_NULL = "userId를 입력해주세요.";
	public static final String BANK_CODE_EMPTY = "은행번호를 입력해주세요.";
	public static final String ACCOUNT_NUMBER_EMPTY = "계좌번호를 입력해 주세요.";
	public static final String BANK_CODE_NOT_THREE = "은행 번호는 3글자 입니다.";
	public static final String AMOUNT_NULL = "금액을 입력해주세요.";
	public static final String ACCOUNT_FORBIDDEN = "현재 사용자는 해당 계좌에 대한 권한이 없습니다.";
	public static final String WRONG_ACCOUNT = "잘못된 계좌 번호 입니다.";
	public static final String ACCOUNT_NOT_ACTIVE = "입금 계좌가 활성화 상태가 아닙니다.";
	public static final String INSUFFICIENT_BALANCE = "잔액이 부족합니다.";
	public static final String ACCOUNT_HOLDER_EMPTY = "계좌 소유주 실명을 입력해 주세요.";
	public static final String WIRE_TRANSFER_ID_WRONG = "타행 이체 식별번호에 해당하는 거래내역이 없습니다.";
	public static final String WIRE_BANK_ERROR = "거래 은행이 응답하지 않습니다.";
	public static final String SAME_ACCOUNT_TRANSFER = "동일한 계좌로 이체할 수 없습니다.";
	public static final String NOT_WIRE_TRANSFER = "당행 이체는 당행 이체 API를 사용해 주세요.";
	public static final String BLANK_TRANSFER_ID = "타행 이체 식별 번호는 비어있을 수 없습니다.";
	public static final String SUCCESS_VALUE_NULL = "성공 여부는 비어있을 수 없습니다.";
	public static final String CALLBACK_URL_BLANK = "콜백 주소를 입력해 주세요.";
	public static final String USER_NOT_FOUND = "존재하지 않는 사용자 입니다.";
	public static final String WITHDRAWAL_ACCOUNT_INFO_NULL = "출금 계좌에 대한 정보를 입력해 주세요.";
	public static final String DEPOSIT_ACCOUNT_INFO_NULL = "입금 계좌에 대한 정보를 입력해 주세요.";
	public static final String TRANSFER_PROGRESS_NOT_DEFINED = "이체 상태를 알 수 없습니다.";
	public static final String ACCOUNT_EMPTY = "계좌를 입력해 주세요.";
	public static final String REQUEST_TIME_NULL = "요청 시간이 비어있습니다.";
	public static final String REQUEST_NOT_IN_TIME = "요청시간이 잘못되었습니다.";
}
