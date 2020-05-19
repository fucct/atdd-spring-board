package spring.board.demo.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import spring.board.demo.dto.ArticleResponse;

class ArticleControllerTest extends AcceptanceTest {

    @DisplayName("게시글을 관리한다")
    @Test
    public void manageArticle() {
        // given

        // when
        Long article1 = createArticle("안녕하세요", "디디", "안녕하세요 좋은하루 되세요");
        Long article2 = createArticle("반갑습니다", "노루", "아이구 졸려라");
        // then
        List<ArticleResponse> articles = getArticles();
        assertThat(articles).hasSize(2)
            .extracting(ArticleResponse::getTitle)
            .containsExactly("안녕하세요", "반갑습니다");
        assertThat(articles).extracting(ArticleResponse::getId).containsExactly(article1, article2);
        assertThat(articles).extracting(ArticleResponse::getUserName).containsExactly("디디", "노루");
        assertThat(articles).extracting(ArticleResponse::getContent)
            .containsExactly("안녕하세요 좋은하루 되세요", "아이구 졸려라");

        //when
        ArticleResponse response1 = getArticle(article1);
        ArticleResponse response2 = getArticle(article2);

        //then
        assertThat(response1.getTitle()).isEqualTo("안녕하세요");
        assertThat(response1.getUserName()).isEqualTo("디디");
        assertThat(response1.getContent()).isEqualTo("안녕하세요 좋은하루 되세요");
        assertThat(response2.getTitle()).isEqualTo("반갑습니다");
        assertThat(response2.getUserName()).isEqualTo("노루");
        assertThat(response2.getContent()).isEqualTo("아이구 졸려라");

        //when
        updateArticle(response1.getId(), "안녕히가세요", "이 바보야");
        updateArticle(response2.getId(), "갑수목장", "싫어요");

        //then
        ArticleResponse response3 = getArticle(response1.getId());
        ArticleResponse response4 = getArticle(response2.getId());

        assertThat(response3.getTitle()).isEqualTo("안녕히가세요");
        assertThat(response3.getContent()).isEqualTo("이 바보야");
        assertThat(response4.getTitle()).isEqualTo("갑수목장");
        assertThat(response4.getContent()).isEqualTo("싫어요");

        //when
        deleteArticle(response1.getId());

        //then
        List<ArticleResponse> articles1 = getArticles();
        assertThat(articles1.size()).isEqualTo(1);

        //when
        deleteArticle(response2.getId());

        //then
        List<ArticleResponse> articles2 = getArticles();
        assertThat(articles2.size()).isEqualTo(0);
    }
}