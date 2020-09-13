package spring.board.demo.articles.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import spring.board.demo.acceptance.AcceptanceTest;
import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.view.dto.AccountCreateResponse;
import spring.board.demo.accounts.view.dto.AccountDetailResponse;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.view.dto.ArticleCreateResponse;
import spring.board.demo.articles.view.dto.ArticleDetailResponse;
import spring.board.demo.articles.view.dto.ArticlePreviewResponse;
import spring.board.demo.infra.dto.TokenResponse;

class ArticleAcceptanceTest extends AcceptanceTest {

    @DisplayName("게시글을 관리한다")
    @TestFactory
    public Stream<DynamicTest> manageArticle() {
        AccountCreateResponse account1 = createUser(AccountFixture.EMAIL1, AccountFixture.NAME1,
            AccountFixture.PASSWORD1);
        AccountCreateResponse user2 = createUser(AccountFixture.EMAIL2, AccountFixture.NAME2,
            AccountFixture.PASSWORD2);
        TokenResponse token1 = login(AccountFixture.EMAIL1, AccountFixture.PASSWORD1);
        TokenResponse token2 = login(AccountFixture.EMAIL2, AccountFixture.PASSWORD2);

        return Stream.of(
            DynamicTest.dynamicTest("Create user's article", () -> {
                ArticleCreateResponse article = createArticle(token1, ArticleFixture.TITLE1,
                    ArticleFixture.CONTENT1);
                assertThat(article).extracting(ArticleCreateResponse::getId).isEqualTo(1L);
                AccountDetailResponse user = getAccount(account1.getId());
                assertThat(user.getArticles()).hasSize(1);
                // assertThat(getArticles()).extracting(ArticlePreviewResponse::getNickname)
                //     .containsExactly(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Create another user's article", () -> {
                    createArticle(token2, ArticleFixture.TITLE1, ArticleFixture.CONTENT1);
                    AccountDetailResponse user = getAccount(user2.getId());
                    assertThat(user.getArticles()).hasSize(1);
                    // assertThat(getArticles()).extracting(ArticlePreviewResponse::getNickname)
                    //     .containsExactly(TEST_USER_NAME, TEST_OTHER_USER_NAME);
                }
            ),
            DynamicTest.dynamicTest("Get a Article", () -> {
                ArticleDetailResponse article = getArticle(ArticleFixture.ID1);
                assertThat(article.getTitle()).isEqualTo(ArticleFixture.TITLE1);
                assertThat(article.getContent()).isEqualTo(ArticleFixture.CONTENT1);
                // assertThat(article.getUserName()).isEqualTo(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Get All Articles", () -> {
                List<ArticlePreviewResponse> articles = getAllArticles();
                assertThat(articles).hasSize(2);
            }),
            DynamicTest.dynamicTest("Update Article", () -> {
                updateArticle(token1, ArticleFixture.ID1, "NEW_" + ArticleFixture.TITLE1,
                    "NEW_" + ArticleFixture.CONTENT1);
                ArticleDetailResponse articleResponse = getArticle(ArticleFixture.ID1);
                assertThat(articleResponse.getTitle()).isEqualTo("NEW_" + ArticleFixture.TITLE1);
                assertThat(articleResponse.getContent()).isEqualTo("NEW_" + ArticleFixture.CONTENT1);
            }),
            DynamicTest.dynamicTest("Delete Article", () -> {
                deleteArticle(token1, ArticleFixture.ID1);
                assertThat(getAccount(account1.getId()).getArticles()).hasSize(0);
            })
        );
    }
}
