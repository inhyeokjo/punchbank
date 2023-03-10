create table pb_account
(
    account_number varchar(255) not null comment '계좌번호' primary key,
    created_at     datetime     not null comment '레코드 생성 시간',
    modified_at    datetime     not null comment '레코드 변경 시간',
    balance        bigint       not null comment '계좌 잔액',
    owner_id       bigint       not null comment '계좌 주인 id',
    state          varchar(255) not null comment '계좌 상태',
    owner_name     varchar(255) not null comment '계좌 주인 실명'
)
    comment '계좌 테이블';

create index idx_owner_id on pb_account (owner_id);
