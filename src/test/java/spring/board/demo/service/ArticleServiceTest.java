package spring.board.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.domain.comments.CommentRepository;

@ExtendWith(SpringExtension.class)
class ArticleServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CommentRepository commentRepository;

    private ArticleService articleService;

    private Account account;
    private Article article;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(accountService, articleRepository, commentRepository);
        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
        article = Article.of(TEST_ID, account, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
    }

    @Test
    void save() {
        when(articleRepository.save(any())).thenReturn(article);
        when(accountService.save(any())).thenReturn(account);

        ArticleCreateResponse response = articleService.save(account,
            new ArticleRequest(TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT));
        assertThat(response)
            .hasFieldOrPropertyWithValue("id", TEST_ID);
    }

    @Test
    void getAll() {
        when(articleRepository.findAllWithAccountName()).thenReturn(
            Collections.singletonList(ArticlePreviewResponse.of(article, account.getName())));

        assertThat(articleService.getArticles())
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(ArticlePreviewResponse.of(article, account.getName()));
    }

    @Test
    void get() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        when(accountService.findById(any())).thenReturn(account);

        assertThat(articleService.getArticle(TEST_ID)).isEqualToComparingFieldByField(
            ArticleDetailResponse.of(article, account, new ArrayList<>()));
        // TODO: 2020/06/10 Comment 추가해서 다시 테스트
    }

    @Test
    void update() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        articleService.update(TEST_ID, account,
            new ArticleRequest("NEW_" + TEST_ARTICLE_TITLE, "NEW_" + TEST_ARTICLE_CONTENT));
        assertThat(article)
            .hasFieldOrPropertyWithValue("title", "NEW_" + TEST_ARTICLE_TITLE)
            .hasFieldOrPropertyWithValue("content", "NEW_" + TEST_ARTICLE_CONTENT);
    }

    @Test
    void delete() {
        articleService.delete(TEST_ID, account);
    }
}
