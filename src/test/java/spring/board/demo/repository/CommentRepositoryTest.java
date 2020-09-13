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

}
