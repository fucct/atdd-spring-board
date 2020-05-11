create table if not exists ARTICLE
(
   id bigint auto_increment not null,
   title varchar(255) not null,
   user_name varchar(255) not null,
   content varchar(1000) not null,
   primary key(id)
);
