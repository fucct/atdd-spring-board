set foreign_key_checks = 0;
TRUNCATE TABLE user;
TRUNCATE TABLE article;
TRUNCATE TABLE comments;
TRUNCATE TABLE user_article;
TRUNCATE TABLE article_user;
set foreign_key_checks = 1;