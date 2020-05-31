package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserResponse;

class ArticleAcceptanceTest extends AcceptanceTest {

    private static final String TEST_ARTICLE_TITLE = "안녕하세요";
    private static final String TEST_ARTICLE_CONTENT = "안녕하십니까. 우아한 테크코스 2기 디디 김태헌입니다.";

    @DisplayName("게시글을 관리한다")
    @TestFactory
    public Stream<DynamicTest> manageArticle() {
        UserCreateResponse user1 = createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        UserCreateResponse user2 = createUser(TEST_OTHER_USER_ID, TEST_OTHER_USER_NAME,
            TEST_OTHER_USER_PASSWORD);
        TokenResponse token1 = login(TEST_USER_ID, TEST_USER_PASSWORD);
        TokenResponse token2 = login(TEST_OTHER_USER_ID, TEST_OTHER_USER_PASSWORD);

        return Stream.of(
            DynamicTest.dynamicTest("Create user's article", () -> {
                createArticle(token1, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
                UserResponse user = getUser(user1.getId(), token1);
                assertThat(user.getArticles()).hasSize(1);
                assertThat(getArticles()).extracting(ArticleResponse::getUserName)
                    .containsExactly(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Create another user's article", () -> {
                    createArticle(token2, TEST_ARTICLE_TITLE, TEST_ARTICLE_CONTENT);
                    UserResponse user = getUser(user2.getId(), token2);
                    assertThat(user.getArticles()).hasSize(1);
                    assertThat(getArticles()).extracting(ArticleResponse::getUserName)
                        .containsExactly(TEST_USER_NAME, TEST_OTHER_USER_NAME);
                }
            ),
            DynamicTest.dynamicTest("Get a Article", () -> {
                ArticleResponse article = getArticle(TEST_ID);
                assertThat(article.getTitle()).isEqualTo(TEST_ARTICLE_TITLE);
                assertThat(article.getContent()).isEqualTo(TEST_ARTICLE_CONTENT);
                assertThat(article.getUserName()).isEqualTo(TEST_USER_NAME);
            }),
            DynamicTest.dynamicTest("Get All Articles", () -> {
                List<ArticleResponse> articles = getAllArticles();
                assertThat(articles).hasSize(2);
            }),
            DynamicTest.dynamicTest("Update Article", () -> {
                updateArticle(token1, TEST_ID, "NEW_" + TEST_ARTICLE_TITLE,
                    "NEW_" + TEST_ARTICLE_CONTENT);
                ArticleResponse articleResponse = getArticle(TEST_ID);
                assertThat(articleResponse.getTitle()).isEqualTo("NEW_" + TEST_ARTICLE_TITLE);
                assertThat(articleResponse.getContent()).isEqualTo("NEW_" + TEST_ARTICLE_CONTENT);
            }),
            DynamicTest.dynamicTest("Delete Article", () -> {
                deleteArticle(token1, TEST_ID);
                assertThat(getUser(user1.getId(), token1).getArticles()).hasSize(0);
            })
        );
    }
}