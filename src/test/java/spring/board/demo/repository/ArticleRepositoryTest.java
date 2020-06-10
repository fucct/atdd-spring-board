package spring.board.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.articles.CommentRef;
import spring.board.demo.domain.comments.Comment;
import spring.board.demo.domain.comments.CommentRepository;

@DataJdbcTest
public class ArticleRepositoryTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void name() {
        Account account1 = Account.of("abcd", "디디", "1234");
        Account account2 = Account.of("1234", "노루", "abcd");
        Article article1 = Article.of(2L, "안녕하세요", "디디입니다");
        accountRepository.saveAll(Arrays.asList(account1, account2));
        articleRepository.save(article1);
        Comment comment1 = Comment.builder()
            .accountId(2L)
            .content("안녕")
            .build();
        Comment comment2 = Comment.builder()
            .accountId(3L)
            .content("메롱")
            .build();
        Iterable<Comment> comments = commentRepository.saveAll(Arrays.asList(comment1, comment2));
        article1.addComment(comment1);
        article1.addComment(comment2);
        articleRepository.save(article1);
        assertThat(comments).hasSize(2);
        List<Long> ids = article1.getComments().stream().map(CommentRef::getComment).collect(
            Collectors.toList());
        List<Comment> responses = commentRepository.findAllById(ids);
        assertThat(responses).hasSize(2)
            .extracting(Comment::getContent)
            .containsExactlyInAnyOrder("안녕", "메롱");

        // List<Account> all = accountRepository.findAll();
        // List<Article> all1 = articleRepository.findAll();
        // Optional<AccountSampleDto> sampleById = accountRepository.findSampleById(2L);
    }
}
