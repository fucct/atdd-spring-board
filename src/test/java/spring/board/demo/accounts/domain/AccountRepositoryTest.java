package spring.board.demo.accounts.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.CommentRepository;

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
    }
}
