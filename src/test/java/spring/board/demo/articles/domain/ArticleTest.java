package spring.board.demo.articles.domain;

import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.domain.Article;

class ArticleTest {

    private Account account;
    private Article article;

    @BeforeEach
    void setUp() {
        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}
