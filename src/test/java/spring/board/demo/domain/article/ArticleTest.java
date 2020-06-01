package spring.board.demo.domain.article;

import static org.assertj.core.api.Assertions.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spring.board.demo.domain.article.dto.ArticleRequest;
import spring.board.demo.domain.user.User;

class ArticleTest {

    private User user;
    private Article article;

    @BeforeEach
    void setUp() {
        user = User.of(TEST_ID, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        article = Article.of(TEST_ID, TEST_ARTICLE_TITLE, user, TEST_ARTICLE_CONTENT);
    }

    @Test
    void create() {
        assertThat(Article.of(TEST_ID, TEST_ARTICLE_TITLE, user, TEST_ARTICLE_CONTENT))
            .isInstanceOf(User.class);
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