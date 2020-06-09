package spring.board.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.comments.Comment;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.service.AccountSampleDto;

@DataJdbcTest
@Slf4j
public class AccountRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void sample() {
        Account account1 = Account.of("abcd", "디디", "1234");
        Account account2 = Account.of("1234", "노루", "abcd");
        Article article1 = Article.of(2L, "안녕하세요", "디디입니다");
        accountRepository.saveAll(Arrays.asList(account1, account2));
        articleRepository.save(article1);
        assertThat(accountRepository.findAll()).hasSize(3);
        Comment comment1 = Comment.builder()
            .articleId(1L)
            .accountId(2L)
            .content("안녕")
            .build();
        Comment comment2 = Comment.builder()
            .articleId(1L)
            .accountId(3L)
            .content("메롱")
            .build();
        Iterable<Comment> comments = commentRepository.saveAll(Arrays.asList(comment1, comment2));
        assertThat(comments).hasSize(2);
        List<CommentDetailResponse> commentResponses = commentRepository.findCommentsByIds(
            Arrays.asList(1L, 2L));
        assertThat(commentResponses).hasSize(2).extracting(CommentDetailResponse::getAccountName)
            .containsExactlyInAnyOrder("디디", "노루");

        List<Account> all = accountRepository.findAll();
        List<Article> all1 = articleRepository.findAll();
        Optional<AccountSampleDto> sampleById = accountRepository.findSampleById(2L);
    }
}
