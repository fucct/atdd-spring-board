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
    }
}
