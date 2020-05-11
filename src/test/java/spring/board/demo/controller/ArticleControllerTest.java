package spring.board.demo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.dto.ArticleResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @DisplayName("게시글 관리 테스트")
    @Test
    public void manageArticle() throws Exception {
        // given

        // when
        createArticle("안녕하세요", "디디", "안녕하세요 좋은하루 되세요");
        createArticle("반갑습니다", "노루", "아이구 졸려라");
        // then
        List<ArticleResponse> articles = getArticles();
        assertThat(articles.size()).isEqualTo(2);

        //when
        ArticleResponse response1 = getArticle(1L);
        ArticleResponse response2 = getArticle(2L);
        assertThat(response1.getTitle()).isEqualTo("안녕하세요");
        assertThat(response1.getUserName()).isEqualTo("디디");
        assertThat(response1.getContent()).isEqualTo("안녕하세요 좋은하루 되세요");
        assertThat(response2.getTitle()).isEqualTo("반갑습니다");
        assertThat(response2.getUserName()).isEqualTo("노루");
        assertThat(response2.getContent()).isEqualTo("아이구 졸려라");

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

    private void createArticle(String title, String userName, String content) {
        Map<String, String> param = new HashMap<>();
        param.put("title", title);
        param.put("userName", userName);
        param.put("content", content);

        given().
            body(param).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/articles").
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value());
    }
}