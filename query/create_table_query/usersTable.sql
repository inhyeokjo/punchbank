create table pb_user
(
    user_id     bigint       not null comment '사용자 고유 식별번호' primary key,
    created_at  datetime     null comment '사용자 생성 시각',
    modified_at datetime     null comment '사용자 정보 마지막 수정 시각',
    `name`      varchar(255) null comment '사용자 실명',
    `use`       bit          not null comment '사용자 활성화 상태'
) comment '사용자 계정 테이블';