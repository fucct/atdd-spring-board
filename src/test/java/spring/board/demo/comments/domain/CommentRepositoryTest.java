package spring.board.demo.comments.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.jdbc.Sql;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.comments.CommentRepository;

@DataJdbcTest
@Slf4j
@Sql("/truncate.sql")
public class CommentRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

}
