drop table if exists article;
drop table if exists comments;

create table if not exists article
(
    id           bigint auto_increment not null,
    title        varchar(255)          not null,
    user_name    varchar(255)          not null,
    content      varchar(1000)         not null,
    created_date datetime,
    updated_date datetime,
    primary key (id)
);

create table if not exists comments
(
    id bigint auto_increment primary key not null ,
    article        bigint       not null,
    comment_user_name varchar(255) not null,
    comment_content   varchar(255) not null,
    created_date datetime,
    updated_date datetime,
    foreign key (article) references article(id)
);


