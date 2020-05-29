package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.UserResponse;

class ArticleAcceptanceTest extends AcceptanceTest {

    private static final String TEST_ARTICLE_TITLE = "안녕하세요";
    private static final String TEST_ARTICLE_CONTENT = "안녕하십니까. 우아한 테크코스 2기 디디 김태헌입니다.";

    @DisplayName("게시글을 관리한다")
    @TestFactory
    public Stream<DynamicTest> manageArticle() {
        createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        TokenResponse token = login(TEST_USER_ID, TEST_USER_PASSWORD);

        return Stream.of(
            DynamicTest.dynamicTest("Create Article", () -> {
                createArticle(token, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
                UserResponse user = getUser(token);
                assertThat(user.getArticles()).hasSize(1);
            }),
            DynamicTest.dynamicTest("Get User's Articles", () -> {
                List<ArticleResponse> articles = getArticles(token);
                assertThat(articles).hasSize(1)
                    .extracting(ArticleResponse::getUserName)
                    .containsExactly(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Get All Articles", () -> {
                List<ArticleResponse> articles = getAllArticles();
                assertThat(articles).hasSize(1);
            })
        );
    }

    private List<ArticleResponse> getAllArticles() {
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

    private List<ArticleResponse> getArticles(TokenResponse token) {
        //@formatter:off
        return given().
                cookie("token", token.getAccessToken()).
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