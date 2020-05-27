package spring.board.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.article.ArticleRepository;

@SpringBootTest
@Sql("/truncate.sql")
class BoardApplicationTests {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void contextLoads() {
        // Article article = Article.of("안녕하세요", "디디", "반갑습니다.");
        //
        // Article persistArticle = articleRepository.save(article);
        //
        // Comment comment1 = Comment.of("ㅂㅅㅋㅋ");
        // Comment comment2 = Comment.of("마닥치라");
        //
        // persistArticle.addComment(comment1);
        // persistArticle.addComment(comment2);
        //
        // articleRepository.save(persistArticle);
    }

}
