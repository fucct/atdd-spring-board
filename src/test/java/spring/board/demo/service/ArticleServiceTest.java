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
    }

    @Test
    void save() {

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
    void update() {
    }

    @Test
    void delete() {
        articleService.delete(TEST_ID, account);
    }
}
