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
        article = Article.of(TEST_ID, account, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
    }

    @Test
    void create() {
        assertThat(Article.of(TEST_ID, account, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT))
            .isInstanceOf(Article.class);
    }

    @Test
    void update() {
        article.update(
            new ArticleRequest("NEW_" + TEST_ARTICLE_TITLE, "NEW_" + TEST_ARTICLE_CONTENT));
        assertThat(article)
            .hasFieldOrPropertyWithValue("title", "NEW_" + TEST_ARTICLE_TITLE)
            .hasFieldOrPropertyWithValue("content", "NEW_" + TEST_ARTICLE_CONTENT);
    }
}