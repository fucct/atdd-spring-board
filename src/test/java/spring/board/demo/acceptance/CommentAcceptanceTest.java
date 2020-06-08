package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.comment.dto.CommentResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.users.dto.UserCreateResponse;

public class CommentAcceptanceTest extends AcceptanceTest {

    @TestFactory
    Stream<DynamicTest> comment() {
        // Given
        UserCreateResponse user1 = createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        UserCreateResponse user2 = createUser(TEST_OTHER_USER_ID, TEST_OTHER_USER_NAME,
            TEST_OTHER_USER_PASSWORD);
        TokenResponse token1 = login(TEST_USER_ID, TEST_USER_PASSWORD);
        TokenResponse token2 = login(TEST_OTHER_USER_ID, TEST_OTHER_USER_PASSWORD);
        ArticleResponse article = createArticle(token1, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);

        return Stream.of(
            DynamicTest.dynamicTest("Create comment", () -> {
                CommentResponse response = addComment(token2, article.getId(),
                    TEST_COMMENT_CONTENT);
                assertThat(response)
                    .hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("userId", 3L)
                    .hasFieldOrPropertyWithValue("content", TEST_COMMENT_CONTENT);
            })
        );
    }

    private CommentResponse addComment(TokenResponse token, Long articleId, String content) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);

        //@formatter:off
        return given()
                .cookie("token", token.getAccessToken())
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(params)
            .when()
                .post("/articles/{id}/comments", articleId)
            .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CommentResponse.class);
    }

}
