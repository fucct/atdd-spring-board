package spring.board.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.dto.ArticleRequest;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.user.User;

@ExtendWith(SpringExtension.class)
class ArticleServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private ArticleRepository articleRepository;

    private ArticleService articleService;

    private User user;
    private Article article;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(userService, articleRepository);
        user = User.of(TEST_ID, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        article = Article.of(TEST_ID, TEST_ARTICLE_TITLE, user, TEST_ARTICLE_CONTENT);
    }

    @Test
    void save() {
        when(articleRepository.save(any())).thenReturn(article);
        when(userService.save(any())).thenReturn(user);

        ArticleResponse response = articleService.save(user,
            new ArticleRequest(TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT));
        assertThat(response)
            .hasFieldOrPropertyWithValue("id", TEST_ID)
            .hasFieldOrPropertyWithValue("title", TEST_ARTICLE_TITLE)
            .hasFieldOrPropertyWithValue("content", TEST_ARTICLE_CONTENT)
            .hasFieldOrPropertyWithValue("userName", TEST_USER_NAME);
    }

    @Test
    void getAll() {
        when(articleRepository.findAll()).thenReturn(Arrays.asList(article));

        assertThat(articleService.getArticles())
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(ArticleResponse.of(article));
    }

    @Test
    void get() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        assertThat(articleService.getArticle(TEST_ID)).isEqualToComparingFieldByField(
            ArticleResponse.of(article));
    }

    @Test
    void update() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        user.addArticle(article);
        articleService.update(TEST_ID, user,
            new ArticleRequest("NEW_" + TEST_ARTICLE_TITLE, "NEW_" + TEST_ARTICLE_CONTENT));
        assertThat(article)
            .hasFieldOrPropertyWithValue("title", "NEW_" + TEST_ARTICLE_TITLE)
            .hasFieldOrPropertyWithValue("content", "NEW_" + TEST_ARTICLE_CONTENT);
    }

    @Test
    void delete() {
        user.addArticle(article);
        articleService.delete(TEST_ID, user);
        assertThat(user.getArticles()).hasSize(0);
    }
}
