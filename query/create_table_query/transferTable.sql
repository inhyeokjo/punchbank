create table pb_transfer
(
    created_at                datetime     not null comment '레코드 생성 시간',
    modified_at               datetime     not null comment '레코드 최종 수정 시간',
    transfer_type             varchar(255) not null comment '이체 타입',
    message                   varchar(255) null comment '이체 표기',
    holder                    varchar(255) null comment '계좌 소유주 실명',
    amount                    bigint       null comment '이체 금액',
    balance                   bigint       null comment '이체 후 잔액',
    trading_account_number    varchar(255) null comment '이체 상대 계좌 번호',
    trading_account_name      varchar(255) null comment '이체 상대 계좌 실명',
    trading_account_message   varchar(255) null comment '이체 상대 계좌 표기',
    trading_account_bank_code char(3)      null comment '이체 상대 계좌 은행코드',
    transfer_progress         varchar(255) not null comment '이체 진행 상태',
    error_code                int          null comment '이체 실패 코드',
    account_number            varchar(255) not null comment '계좌 번호',
    transaction_history_id    char(19)     not null comment '트렌젝션 고유 식별 번호(YYYYMMDDHHMMSS+auto increment value 5자리)',
    transfer_id               char(19)     not null comment '거래 고유 식별 번호(YYYYMMDDHHMMSS+auto increment value 5자리)',
    wire_transfer_id          char(25)     null comment '타행 거래 고유 식별 번호 (YYYYMMDDHHMMSS+출금은행코드+입금은행코드+auto increment value 5자리)',
    primary key (account_number, transfer_id)
) comment '이체 내역 테이블';

create index idx_transfer on pb_transfer (created_at, account_number);
