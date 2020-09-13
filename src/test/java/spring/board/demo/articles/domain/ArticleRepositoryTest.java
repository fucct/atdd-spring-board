package spring.board.demo.articles.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.CommentRepository;

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
