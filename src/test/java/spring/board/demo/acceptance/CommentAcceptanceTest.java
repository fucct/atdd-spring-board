package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.domain.comments.dto.CommentResponse;
import spring.board.demo.domain.token.dto.TokenResponse;

public class CommentAcceptanceTest extends AcceptanceTest {

    @TestFactory
    Stream<DynamicTest> comment() {
        // Given
        AccountCreateResponse user1 = createUser(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME,
            TEST_ACCOUNT_PASSWORD);
        AccountCreateResponse user2 = createUser(TEST_OTHER_ACCOUNT_ID, TEST_OTHER_ACCOUNT_NAME,
            TEST_OTHER_ACCOUNT_PASSWORD);
        TokenResponse token1 = login(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_PASSWORD);
        TokenResponse token2 = login(TEST_OTHER_ACCOUNT_ID, TEST_OTHER_ACCOUNT_PASSWORD);
        ArticleCreateResponse article = createArticle(token1, TEST_ARTICLE_TITLE,
            TEST_ARTICLE_CONTENT);
        CommentResponse commentResponse = addComment(token2, article.getId(), TEST_COMMENT_CONTENT);
        return Stream.of(
            DynamicTest.dynamicTest("Create comment", () -> {
                assertThat(commentResponse)
                    .hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("accountId", 2L)
                    .hasFieldOrPropertyWithValue("content", TEST_COMMENT_CONTENT);
            }),
            DynamicTest.dynamicTest("Article has comment", () -> {
                ArticleDetailResponse response = getArticle(article.getId());
                assertThat(response.getComments()).hasSize(1)
                    .extracting(CommentDetailResponse::getAccountName)
                    .containsExactlyInAnyOrder(TEST_OTHER_ACCOUNT_NAME);
            }),
            DynamicTest.dynamicTest("Account has comment", () -> {
                AccountDetailResponse response = getAccount(user2.getId());
                assertThat(response.getComments()).hasSize(1);
            }),
            DynamicTest.dynamicTest("Get comment", () -> {
                CommentDetailResponse detailResponse = getComment(commentResponse.getId());
                assertThat(detailResponse)
                    .hasFieldOrPropertyWithValue("id", commentResponse.getId())
                    .hasFieldOrPropertyWithValue("accountId", commentResponse.getAccountId())
                    .hasFieldOrPropertyWithValue("accountName", TEST_OTHER_ACCOUNT_NAME)
                    .hasFieldOrPropertyWithValue("content", commentResponse.getContent());
            }),
            DynamicTest.dynamicTest("Update Comment", () -> {
                updateComment(commentResponse.getId(), token2, TEST_OTHER_COMMENT_CONTENT);
                assertThat(getComment(commentResponse.getId()))
                    .hasFieldOrPropertyWithValue("content", TEST_OTHER_COMMENT_CONTENT);
            }),
            DynamicTest.dynamicTest("Delete Comment", () -> {
                deleteComment(commentResponse.getId(), token2);
                ArticleDetailResponse articleResponse = getArticle(article.getId());
                assertThat(articleResponse.getComments()).hasSize(0);
            })
        );
    }

    private void deleteComment(Long id, TokenResponse token) {
        //@formatter:off
        given()
                .cookie("token", token.getAccessToken())
                .contentType(APPLICATION_JSON_VALUE)
            .when()
                .delete("/comments/{id}", id)
            .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private void updateComment(Long id, TokenResponse token, String content) {
        Map<String, String> body = new HashMap<>();
        body.put("content", content);

        //@formatter:off
        given()
                .cookie("token", token.getAccessToken())
                .contentType(APPLICATION_JSON_VALUE)
                .body(body)
            .when()
                .put("/comments/{id}", id)
            .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private CommentDetailResponse getComment(Long commentId) {
        return given()
            .accept(APPLICATION_JSON_VALUE)
            .when()
            .get("/comments/{commentId}", commentId)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().as(CommentDetailResponse.class);
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
