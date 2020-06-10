package spring.board.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.jdbc.Sql;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.comments.Comment;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;

@DataJdbcTest
@Slf4j
@Sql("/truncate.sql")
public class CommentRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void create() {
        Account account1 = Account.of("abcd", "디디", "1234");
        Account account2 = Account.of("1234", "노루", "abcd");
        Article article1 = Article.of(account1, "안녕하세요", "디디입니다");
        accountRepository.saveAll(Arrays.asList(account1, account2));
        assertThat(accountRepository.findAll()).hasSize(2);
        Comment comment1 = Comment.builder()
            .accountId(account1.getId())
            .content("안녕")
            .build();
        Comment comment2 = Comment.builder()
            .accountId(account2.getId())
            .content("메롱")
            .build();
        Iterable<Comment> comments = commentRepository.saveAll(Arrays.asList(comment1, comment2));
        assertThat(comments).hasSize(2);
        List<CommentDetailResponse> commentResponses = commentRepository.findCommentsByIds(
            Arrays.asList(account1.getId(), account2.getId()));
        assertThat(commentResponses).hasSize(2).extracting(CommentDetailResponse::getAccountName)
            .containsExactlyInAnyOrder("디디", "노루");

    }
}
