package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.domain.Comment;
import spring.board.demo.dto.ArticleDetailResponse;

public class CommentAcceptanceTest extends AcceptanceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @Test
    @DisplayName("댓글을 관리한다")
    void comment() {
        //given
        Long articleId = createArticle("안녕하세요", "디디", "반갑습니다. 우아한 테크코스 디디입니다.");
        ArticleDetailResponse article = getDetailArticle(articleId);

        assertThat(article.getComments()).hasSize(0);

        Long commentId1 = addComment(articleId, "카일", "디디님, 안녕하세요");
        Long commentId2 = addComment(articleId, "히로", "어, 디디, 하이");

        ArticleDetailResponse article2 = getDetailArticle(articleId);
        assertThat(article2.getComments()).hasSize(2);
        assertThat(article2.getComments()).extracting(Comment::getUserName)
            .containsExactly("카일", "히로");
        assertThat(article2.getComments()).extracting(Comment::getContent)
            .containsExactly("디디님, 안녕하세요", "어, 디디, 하이");
    }


}
