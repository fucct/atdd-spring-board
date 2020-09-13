package spring.board.demo.comments.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;

import spring.board.demo.acceptance.AcceptanceTest;
import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.view.dto.AccountCreateResponse;
import spring.board.demo.accounts.view.dto.AccountDetailResponse;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.view.dto.ArticleCreateResponse;
import spring.board.demo.articles.view.dto.ArticleDetailResponse;
import spring.board.demo.comments.CommentFixture;
import spring.board.demo.comments.view.dto.CommentDetailResponse;
import spring.board.demo.comments.view.dto.CommentResponse;
import spring.board.demo.infra.dto.TokenResponse;

public class CommentAcceptanceTest extends AcceptanceTest {

    @TestFactory
    Stream<DynamicTest> comment() {
        // Given
        AccountCreateResponse user1 = createUser(AccountFixture.EMAIL1, AccountFixture.NAME1,
            AccountFixture.PASSWORD1);
        AccountCreateResponse user2 = createUser(AccountFixture.EMAIL2, AccountFixture.NAME2,
            AccountFixture.PASSWORD2);
        TokenResponse token1 = login(AccountFixture.EMAIL1, AccountFixture.PASSWORD1);
        TokenResponse token2 = login(AccountFixture.EMAIL2, AccountFixture.PASSWORD2);
        ArticleCreateResponse article = createArticle(token1, ArticleFixture.TITLE1,
            ArticleFixture.CONTENT1);
        CommentResponse commentResponse = addComment(token2, article.getId(), CommentFixture.CONTENT1);
        return Stream.of(
            DynamicTest.dynamicTest("Create comment", () -> {
                assertThat(commentResponse)
                    .hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("accountId", 2L)
                    .hasFieldOrPropertyWithValue("content", CommentFixture.CONTENT1);
            }),
            DynamicTest.dynamicTest("Article has comment", () -> {
                ArticleDetailResponse response = getArticle(article.getId());
                assertThat(response.getComments()).hasSize(1)
                    .extracting(CommentDetailResponse::getAccountName)
                    .containsExactlyInAnyOrder(AccountFixture.NAME2);
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
                    .hasFieldOrPropertyWithValue("accountName", AccountFixture.NAME2)
                    .hasFieldOrPropertyWithValue("content", commentResponse.getContent());
            }),
            DynamicTest.dynamicTest("Update Comment", () -> {
                updateComment(commentResponse.getId(), token2, CommentFixture.CONTENT2);
                assertThat(getComment(commentResponse.getId()))
                    .hasFieldOrPropertyWithValue("content", CommentFixture.CONTENT2);
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
