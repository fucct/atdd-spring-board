package spring.board.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.Comment;
import spring.board.demo.comments.CommentFixture;
import spring.board.demo.comments.CommentRepository;

@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void contextLoads() {
        final Account account = AccountFixture.createWithoutId();
        final Article article = ArticleFixture.createWithoutId();
        final Comment comment = CommentFixture.createWithoutId();

        accountRepository.save(account);
        articleRepository.save(article);
        commentRepository.save(comment);
    }

}
