package spring.board.demo.acceptance;

import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
@ActiveProfiles("test")
public class AcceptanceTest {
    public static final Long TEST_ID = 1L;
    public static final Long TEST_OTHER_ID = 2L;
    public static final String TEST_USER_ID = "fucct";
    public static final String TEST_OTHER_USER_ID = "dqrd123";
    public static final String TEST_USER_NAME = "DD";
    public static final String TEST_OTHER_USER_NAME = "TAEHEON";
    public static final String TEST_USER_PASSWORD = "1234";
    public static final String TEST_OTHER_USER_PASSWORD = "qwer";
    public static final String TEST_USER_TOKEN = "testToken";
    public static final String TEST_OTHER_USER_TOKEN = "otherTestToken";
    public static final String TEST_ARTICLE_TITLE = "안녕하세요";
    public static final String TEST_ARTICLE_CONTENT = "안녕하십니까. 우아한 테크코스 2기 디디 김태헌입니다.";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    protected UserCreateResponse createUser(String userID, String name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userID);
        params.put("name", name);
        params.put("password", password);

        //@formatter:off
        return
            given().
                body(params).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
            when().
                post("/users").
            then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                extract().as(UserCreateResponse.class);
    }

    protected UserResponse getUser(Long id, TokenResponse token) {
        //@formatter:off
        return
            given().
                cookie("token", token.getAccessToken()).
                contentType(APPLICATION_JSON_VALUE).
                accept(APPLICATION_JSON_VALUE).
            when().
                get("/users/"+ id).
            then().
                statusCode(HttpStatus.OK.value()).
                extract().as(UserResponse.class);
    }

    protected TokenResponse login(String userId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);

        //@formatter:off
        return
            given().
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

    protected void updateUser(Long id, String name, String oldPassword, String newPassword, TokenResponse token) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);

        //@formatter:off
        given()
            .cookie("token", token.getAccessToken())
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .body(params)
        .when()
            .put("/users/"+id)
        .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected void deleteUser(Long id, TokenResponse tokenResponse) {
        //@formatter:off
        given().
            cookie("token", tokenResponse.getAccessToken()).
        when().
            delete("/users/" + id).
        then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected ArticleResponse createArticle(TokenResponse token, String title, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("content", content);

        //@formatter:off
        return
            given().
                cookie("token", token.getAccessToken()).
                contentType(APPLICATION_JSON_VALUE).
                accept(APPLICATION_JSON_VALUE).
                body(params).
            when().
                post("/articles").
            then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                extract().as(ArticleResponse.class);
    }

    protected void deleteArticle(TokenResponse token, Long articleId) {
        given().
            cookie("token", token.getAccessToken()).
            when().
            delete("/articles/" + articleId).
            then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected void updateArticle(TokenResponse token, Long articleId, String title, String content) {
        //@formatter:off
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("content", content);

        given().
            body(params).
            cookie("token", token.getAccessToken()).
            contentType(APPLICATION_JSON_VALUE).
        when().
            put("/articles/"+articleId).
        then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected ArticleResponse getArticle(Long id) {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles/"+id).
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().as(ArticleResponse.class);
    }

    protected List<ArticleResponse> getAllArticles() {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().jsonPath().
            getList(".", ArticleResponse.class);
    }

    protected List<ArticleResponse> getArticles() {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().jsonPath().
            getList(".", ArticleResponse.class);
    }
}
