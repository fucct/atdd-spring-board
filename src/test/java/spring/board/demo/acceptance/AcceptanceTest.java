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
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import spring.board.demo.domain.article.dto.ArticleCreateResponse;
import spring.board.demo.domain.article.dto.ArticleDetailResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public class AcceptanceTest {
    public static final String TEST_USER_ID = "fucct";
    public static final String TEST_OTHER_USER_ID = "dqrd123";
    public static final String TEST_USER_NAME = "DD";
    public static final String TEST_OTHER_USER_NAME = "TAEHEON";
    public static final String TEST_USER_PASSWORD = "1234";
    public static final String TEST_OTHER_USER_PASSWORD = "qwer";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
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

    protected UserResponse getUser(TokenResponse token) {
        //@formatter:off
        return given().
            cookie("token", token.getAccessToken()).
            when().
            get("/users/mypage")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract().as(UserResponse.class);
    }

    protected UserCreateResponse createUser(String userID, String name, String password) {
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
            extract().as(UserCreateResponse.class);
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

    protected void updateUser(Long id, String name, String oldPassword, String newPassword, TokenResponse token) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);

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

    protected List<UserResponse> getAll() {
        return given().
            when().
            get("/users").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().jsonPath().
            getList(".", UserResponse.class);
    }

    protected void deleteUser(TokenResponse tokenResponse, Long id) {
        given().
            cookie("token", tokenResponse.getAccessToken()).
            when().
            delete("/users/" + id).
            then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected ArticleCreateResponse createArticle(TokenResponse token, String title, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("content", content);

        //@formatter:off
        return given().
            cookie("token", token.getAccessToken()).
            contentType(APPLICATION_JSON_VALUE).
            accept(APPLICATION_JSON_VALUE).
            body(params).
            when().
            post("/articles").
            then().
            log().all().
            statusCode(HttpStatus.CREATED.value()).
            extract().as(ArticleCreateResponse.class);
    }
}
