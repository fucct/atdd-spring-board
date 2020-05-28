package spring.board.demo.domain.article.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String userName;

    public static List<ArticleResponse> listOf(List<Article> articles, String userName) {
        return articles.stream()
            .map(article -> ArticleResponse.of(article, userName))
            .collect(Collectors.toList());
    }

    public static ArticleResponse of(Article article, String userName) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),
            userName);
    }
}
