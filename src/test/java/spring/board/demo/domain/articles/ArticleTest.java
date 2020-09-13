package spring.board.demo.domain.articles;

import static org.assertj.core.api.Assertions.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.dto.ArticleRequest;

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
