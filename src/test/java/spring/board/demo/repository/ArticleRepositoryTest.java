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

        // List<Account> all = accountRepository.findAll();
        // List<Article> all1 = articleRepository.findAll();
        // Optional<AccountSampleDto> sampleById = accountRepository.findSampleById(2L);
    }
}
