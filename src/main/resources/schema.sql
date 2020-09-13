drop table if exists account cascade;
drop table if exists article cascade;
drop table if exists comment cascade;

create table if not exists account
(
    id           bigint auto_increment not null,
    email        varchar(50)           not null unique,
    name         varchar(20)           not null unique,
    password     varchar(20)           not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists account_article
(
    account bigint not null,
    article bigint not null,
    primary key (account, article)
);

create table if not exists article
(
    id           bigint auto_increment not null,
    account_id   bigint                not null,
    title        varchar(256)          not null,
    content      varchar(2048)         not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists article_comment
(
    article bigint not null,
    comment bigint not null,
    primary key (article, comment)
);

create table if not exists comment
(
    id           bigint auto_increment not null,
    account_id   bigint                not null,
    content      varchar(256)          not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);
