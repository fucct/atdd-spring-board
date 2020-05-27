package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.article.dto.ArticleDetailResponse;

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
        // Long articleId = createArticle("안녕하세요", "디디", "반갑습니다. 우아한 테크코스 디디입니다.");
        // ArticleDetailResponse article = getDetailArticle(articleId);
        //
        // assertThat(article.getComments()).hasSize(0);
        //
        // Long commentId1 = addComment(articleId, "카일", "디디님, 안녕하세요");
        // Long commentId2 = addComment(articleId, "히로", "어, 디디, 하이");
        //
        // Comment.CommentResponse commentResponse1 = getComment(articleId, commentId1);
        // Comment.CommentResponse commentResponse2 = getComment(articleId, commentId2);
        //
        // ArticleDetailResponse articleResponse1 = getDetailArticle(articleId);
        // List<Comment> comments1 = articleResponse1.getComments();
        // assertThat(comments1).hasSize(2);
        // assertThat(comments1).extracting(Comment::getUserName)
        //     .containsExactly(commentResponse1.getUserName(), commentResponse2.getUserName());
        // assertThat(comments1).extracting(Comment::getContent)
        //     .containsExactly(commentResponse1.getContent(), commentResponse2.getContent());
        //
        // updateComment(articleId, commentId1, commentResponse1.getUserName(), "디디야 왔냐");
        // updateComment(articleId, commentId2, commentResponse2.getUserName(), "흠");
        //
        // ArticleDetailResponse articleResponse2 = getDetailArticle(articleId);
        // List<Comment> comments2 = articleResponse2.getComments();
        //
        // assertThat(comments2).extracting(Comment::getUserName)
        //     .containsExactly("카일", "히로");
        // assertThat(comments2).extracting(Comment::getContent)
        //     .containsExactly("디디야 왔냐", "흠");
        //
        // deleteComment(articleId, commentId1);
        //
        // ArticleDetailResponse articleResponse3 = getDetailArticle(articleId);
        // List<Comment> comments3 = articleResponse3.getComments();
        // assertThat(comments3).hasSize(1);
        // assertThat(comments3).extracting(Comment::getUserName).containsExactly("히로");
        // assertThat(comments3).extracting(Comment::getContent).containsExactly("흠");
    }

    private void deleteComment(Long articleId, Long commentId) {
        given().when()
            .delete("/articles/" + articleId + "/comments/" + commentId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    private void updateComment(Long articleId, Long commentId, String userName, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("content", content);

        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .when()
            .put("/articles/" + articleId + "/comments/" + commentId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value());
    }

    private Comment.CommentResponse getComment(Long articleId, Long commentId) {
        return given()
            .when()
            .get("/articles/" + articleId + "/comments/" + commentId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(Comment.CommentResponse.class);
    }
}
