create table pb_bank
(
    bank_code char(3)      not null comment '은행 코드' primary key,
    bank_name varchar(255) null comment '은행 이름'
) comment '은행 테이블';

