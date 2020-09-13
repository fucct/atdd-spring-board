package spring.board.demo.articles.domain;

import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.Article;

class ArticleTest {

    private Account account;
    private Article article;

    @BeforeEach
    void setUp() {
        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}
