package spring.board.demo;

import java.util.Arrays;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import spring.board.demo.domain.Article;
import spring.board.demo.domain.Comment;
import spring.board.demo.repository.ArticleRepository;

@SpringBootTest
class BoardApplicationTests {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void contextLoads() {
        Article article = Article.of("안녕하세요", "디디", "반갑습니다.");

        Article persistArticle = articleRepository.save(article);

        Comment comment1 = Comment.of( "카일", "ㅂㅅㅋㅋ");
        Comment comment2 = Comment.of( "호돌", "마닥치라");

        persistArticle.addComment(comment1);
        persistArticle.addComment(comment2);

        articleRepository.save(persistArticle);
    }

}
