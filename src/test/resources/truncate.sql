set foreign_key_checks = 0;
TRUNCATE TABLE user;
TRUNCATE TABLE article;
TRUNCATE TABLE comments;
TRUNCATE TABLE user_comment;
TRUNCATE TABLE article_comment;
TRUNCATE TABLE user_article;
set foreign_key_checks = 1;