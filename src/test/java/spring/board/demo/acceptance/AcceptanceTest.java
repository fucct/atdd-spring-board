package spring.board.demo.acceptance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.domain.article.dto.ArticleDetailResponse;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public class AcceptanceTest {
    protected static final String TEST_USER_ID = "fucct";
    protected static final String TEST_OTHER_USER_ID = "dqrd123";
    protected static final String TEST_USER_NAME = "DD";
    protected static final String TEST_OTHER_USER_NAME = "TAEHEON";
    protected static final String TEST_USER_PASSWORD = "1234";
    protected static final String TEST_OTHER_USER_PASSWORD = "qwer";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    protected void deleteArticle(Long id) {
        given().when().
            delete("/articles/" + id).
            then().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected void updateArticle(Long id, String title, String content) {
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

    protected ArticleResponse getArticle(Long id) {
        return given().when().
            get("/articles/" + id).
            then().
            statusCode(HttpStatus.OK.value()).
            extract().
            jsonPath().getObject(".", ArticleResponse.class);
    }

    protected List<ArticleResponse> getArticles() {
        return given().when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().
            jsonPath().getList(".", ArticleResponse.class);
    }

    protected Long createArticle(String title, String userName, String content) {
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

    protected Long addComment(Long articleId, String userName, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("content", content);

        return given().
            body(params).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/articles/" + articleId + "/comments").
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            extract().as(Long.class);
    }

    protected ArticleDetailResponse getDetailArticle(Long articleId) {
        return given().when().
            get("/articles/detail/" + articleId).
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().as(ArticleDetailResponse.class);
    }

    protected User getUser(TokenResponse token) {
        //@formatter:off
        return given().
            cookie("token", token.getAccessToken()).
            when().
            get("/users/mypage")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(User.class);
    }

    protected Long createUser(String userID, String name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userID);
        params.put("name", name);
        params.put("password", password);

        //@formatter:off
        return given().
            body(params).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/users").
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            extract().as(Long.class);
    }

    protected TokenResponse login(String userId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);

        //@formatter:off
        return given().
            body(params).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            accept(MediaType.APPLICATION_JSON_VALUE).
            when().
            post("/users/login").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().as(TokenResponse.class);
    }
}
