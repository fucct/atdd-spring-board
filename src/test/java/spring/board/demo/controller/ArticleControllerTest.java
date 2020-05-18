package spring.board.demo.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.dto.ArticleResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
class ArticleControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @DisplayName("게시글을 관리한다")
    @Test
    public void manageArticle() {
        // given

        // when
        Long article1 = createArticle("안녕하세요", "디디", "안녕하세요 좋은하루 되세요");
        Long article2 = createArticle("반갑습니다", "노루", "아이구 졸려라");
        // then
        List<ArticleResponse> articles = getArticles();
        assertThat(articles).hasSize(2)
            .extracting(ArticleResponse::getTitle)
            .containsExactly("안녕하세요", "반갑습니다");
        assertThat(articles).extracting(ArticleResponse::getId).containsExactly(article1, article2);
        assertThat(articles).extracting(ArticleResponse::getUserName).containsExactly("디디", "노루");
        assertThat(articles).extracting(ArticleResponse::getContent)
            .containsExactly("안녕하세요 좋은하루 되세요", "아이구 졸려라");

        //when
        ArticleResponse response1 = getArticle(article1);
        ArticleResponse response2 = getArticle(article2);

        //then
        assertThat(response1.getTitle()).isEqualTo("안녕하세요");
        assertThat(response1.getUserName()).isEqualTo("디디");
        assertThat(response1.getContent()).isEqualTo("안녕하세요 좋은하루 되세요");
        assertThat(response2.getTitle()).isEqualTo("반갑습니다");
        assertThat(response2.getUserName()).isEqualTo("노루");
        assertThat(response2.getContent()).isEqualTo("아이구 졸려라");

        //when
        updateArticle(response1.getId(), "안녕히가세요", "이 바보야");
        updateArticle(response2.getId(), "갑수목장", "싫어요");

        //then
        ArticleResponse response3 = getArticle(response1.getId());
        ArticleResponse response4 = getArticle(response2.getId());

        assertThat(response3.getTitle()).isEqualTo("안녕히가세요");
        assertThat(response3.getContent()).isEqualTo("이 바보야");
        assertThat(response4.getTitle()).isEqualTo("갑수목장");
        assertThat(response4.getContent()).isEqualTo("싫어요");

        //when
        deleteArticle(response1.getId());

        //then
        List<ArticleResponse> articles1 = getArticles();
        assertThat(articles1.size()).isEqualTo(1);

        //when
        deleteArticle(response2.getId());

        //then
        List<ArticleResponse> articles2 = getArticles();
        assertThat(articles2.size()).isEqualTo(0);
    }

    private void deleteArticle(Long id) {
        given().when().
            delete("/articles/" + id).
            then().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void updateArticle(Long id, String title, String content) {
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        param.put("content", content);

        given().
            body(param).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            put("/articles/" + id).
            then().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    private ArticleResponse getArticle(Long id) {
        return given().when().
            get("/articles/" + id).
            then().
            statusCode(HttpStatus.OK.value()).
            extract().
            jsonPath().getObject(".", ArticleResponse.class);
    }

    private List<ArticleResponse> getArticles() {
        return given().when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().
            jsonPath().getList(".", ArticleResponse.class);
    }

    private Long createArticle(String title, String userName, String content) {
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        param.put("userName", userName);
        param.put("content", content);

        return given().
            body(param).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/articles").
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            extract().as(Long.class);
    }
}