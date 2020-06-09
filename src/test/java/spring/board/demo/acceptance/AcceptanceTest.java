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
import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.token.dto.TokenResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
@ActiveProfiles("test")
public class AcceptanceTest {
    public static final Long TEST_ID = 1L;
    public static final Long TEST_OTHER_ID = 2L;
    public static final String TEST_ACCOUNT_EMAIL = "fucct";
    public static final String TEST_OTHER_ACCOUNT_ID = "dqrd123";
    public static final String TEST_ACCOUNT_NAME = "DD";
    public static final String TEST_OTHER_ACCOUNT_NAME = "TAEHEON";
    public static final String TEST_ACCOUNT_PASSWORD = "1234";
    public static final String TEST_OTHER_ACCOUNT_PASSWORD = "qwer";
    public static final String TEST_ACCOUNT_TOKEN = "testToken";
    public static final String TEST_OTHER_ACCOUNT_TOKEN = "otherTestToken";
    public static final String TEST_ARTICLE_TITLE = "안녕하세요";
    public static final String TEST_ARTICLE_CONTENT = "안녕하십니까. 우아한 테크코스 2기 디디 김태헌입니다.";
    public static final String TEST_COMMENT_CONTENT = "안녕하세용";
    public static final String TEST_OTHER_COMMENT_CONTENT = "반가워용";

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    protected AccountCreateResponse createUser(String email, String name, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("name", name);
        params.put("password", password);

        //@formatter:off
        return
            given().
                body(params).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
            when().
                post("/accounts").
            then().
                log().all().
                statusCode(HttpStatus.CREATED.value()).
                extract().as(AccountCreateResponse.class);
    }

    protected AccountDetailResponse getAccount(Long id) {
        //@formatter:off
        return
            given().
                contentType(APPLICATION_JSON_VALUE).
                accept(APPLICATION_JSON_VALUE).
            when().
                get("/accounts/{id}", id).
            then().
                statusCode(HttpStatus.OK.value()).
                extract().as(AccountDetailResponse.class);
    }

    protected TokenResponse login(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        //@formatter:off
        return
            given().
                body(params).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
            when().
                post("/accounts/login").
            then().
                log().all().
                statusCode(HttpStatus.OK.value()).
                extract().as(TokenResponse.class);
    }

    protected void updateAccount(Long id, String name, String oldPassword, String newPassword, TokenResponse token) {
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
            .put("/accounts/"+id)
        .then()
            .log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected void deleteUser(Long id, TokenResponse tokenResponse) {
        //@formatter:off
        given().
            cookie("token", tokenResponse.getAccessToken()).
        when().
            delete("/accounts/" + id).
        then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
    }

    protected ArticlePreviewResponse createArticle(TokenResponse token, String title, String content) {
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
                extract().as(ArticlePreviewResponse.class);
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

    protected ArticleDetailResponse getArticle(Long id) {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles/{id}",id).
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().as(ArticleDetailResponse.class);
    }

    protected List<ArticlePreviewResponse> getAllArticles() {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().jsonPath().
            getList(".", ArticlePreviewResponse.class);
    }

    protected List<ArticlePreviewResponse> getArticles() {
        //@formatter:off
        return given().
            accept(APPLICATION_JSON_VALUE).
            when().
            get("/articles").
            then().
            log().all().
            statusCode(HttpStatus.OK.value()).
            extract().jsonPath().
            getList(".", ArticlePreviewResponse.class);
    }
}
