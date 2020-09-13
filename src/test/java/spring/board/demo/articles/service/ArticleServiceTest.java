package spring.board.demo.articles.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.service.AccountService;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.service.ArticleService;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.articles.view.dto.ArticlePreviewResponse;
import spring.board.demo.comments.CommentRepository;

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
        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
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
        articleService.delete(ArticleFixture.ID1, account);
    }
}
