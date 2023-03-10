create table pb_transaction_history
(
    transaction_history_id char(19)     not null comment '트렌젝션 고유 식별 번호(YYYYMMDDHHMMSS+auto increment value 5자리)' primary key,
    transaction_date       char(8)      not null comment '트렌젝션 시작 날자(YYYYMMDD)',
    transaction_time       char(12)     not null comment '트렌젝션 발생 시간(HHMMSS+소숫점 아래 6자리)',
    uri                    varchar(255) null comment '요청 uri',
    method                 varchar(255) null comment '요청 method',
    request                text         null comment '요청 세부정보',
    response               text         null comment '응답 세부정보',
    request_user           bigint       not null comment '요청을 보낸 사용자 id',
    start_time             datetime     not null comment '트렌젝션 시작 시각',
    end_time               datetime     null comment '트렌젝션 종료 시각'
) comment '트렌젝션 추적 테이블';

create index idx_request_user on pb_transaction_history (request_user);

create index idx_transaction_time on pb_transaction_history (transaction_date, transaction_time);

