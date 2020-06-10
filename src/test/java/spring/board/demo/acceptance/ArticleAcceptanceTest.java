package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.token.dto.TokenResponse;

class ArticleAcceptanceTest extends AcceptanceTest {

    @DisplayName("게시글을 관리한다")
    @TestFactory
    public Stream<DynamicTest> manageArticle() {
        AccountCreateResponse account1 = createUser(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME,
            TEST_ACCOUNT_PASSWORD);
        AccountCreateResponse user2 = createUser(TEST_OTHER_ACCOUNT_ID, TEST_OTHER_ACCOUNT_NAME,
            TEST_OTHER_ACCOUNT_PASSWORD);
        TokenResponse token1 = login(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_PASSWORD);
        TokenResponse token2 = login(TEST_OTHER_ACCOUNT_ID, TEST_OTHER_ACCOUNT_PASSWORD);

        return Stream.of(
            DynamicTest.dynamicTest("Create user's article", () -> {
                ArticleCreateResponse article = createArticle(token1, TEST_ARTICLE_TITLE,
                    TEST_ARTICLE_CONTENT);
                assertThat(article).extracting(ArticleCreateResponse::getId).isEqualTo(1L);
                AccountDetailResponse user = getAccount(account1.getId());
                assertThat(user.getArticles()).hasSize(1);
                // assertThat(getArticles()).extracting(ArticlePreviewResponse::getNickname)
                //     .containsExactly(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Create another user's article", () -> {
                    createArticle(token2, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
                    AccountDetailResponse user = getAccount(user2.getId());
                    assertThat(user.getArticles()).hasSize(1);
                    // assertThat(getArticles()).extracting(ArticlePreviewResponse::getNickname)
                    //     .containsExactly(TEST_USER_NAME, TEST_OTHER_USER_NAME);
                }
            ),
            DynamicTest.dynamicTest("Get a Article", () -> {
                ArticleDetailResponse article = getArticle(TEST_ID);
                assertThat(article.getTitle()).isEqualTo(TEST_ARTICLE_TITLE);
                assertThat(article.getContent()).isEqualTo(TEST_ARTICLE_CONTENT);
                // assertThat(article.getUserName()).isEqualTo(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Get All Articles", () -> {
                List<ArticlePreviewResponse> articles = getAllArticles();
                assertThat(articles).hasSize(2);
            }),
            DynamicTest.dynamicTest("Update Article", () -> {
                updateArticle(token1, TEST_ID, "NEW_" + TEST_ARTICLE_TITLE,
                    "NEW_" + TEST_ARTICLE_CONTENT);
                ArticleDetailResponse articleResponse = getArticle(TEST_ID);
                assertThat(articleResponse.getTitle()).isEqualTo("NEW_" + TEST_ARTICLE_TITLE);
                assertThat(articleResponse.getContent()).isEqualTo("NEW_" + TEST_ARTICLE_CONTENT);
            }),
            DynamicTest.dynamicTest("Delete Article", () -> {
                deleteArticle(token1, TEST_ID);
                assertThat(getAccount(account1.getId()).getArticles()).hasSize(0);
            })
        );
    }
}