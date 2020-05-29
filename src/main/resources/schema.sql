drop table if exists user cascade;
drop table if exists article cascade;
drop table if exists comments cascade;
drop table if exists user_comment;
drop table if exists article_comment;
drop table if exists user_article;
drop table if exists article_user;

create table if not exists user
(
    id           bigint auto_increment not null,
    user_id      varchar(20)           not null unique,
    name         varchar(20)           not null unique,
    password     varchar(20)           not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists article
(
    id           bigint auto_increment not null,
    title        varchar(255)          not null,
    content      varchar(1000)         not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists comments
(
    id           bigint auto_increment not null,
    user         bigint                not null,
    content      varchar(255)          not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists user_comment
(
    user    bigint not null,
    comment bigint not null
);

create table if not exists article_comment
(
    article bigint not null,
    comment bigint not null references comments (id)
);

create table if not exists user_article
(
    user    bigint not null,
    article bigint not null,
    primary key (user, article)
);

create table if not exists article_user
(
    article   bigint       not null,
    user      bigint       not null,
    user_name varchar(255) not null,
    primary key (user, article)
);





