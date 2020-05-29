package spring.board.demo.domain.article.dto;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ArticleResponse> listOf(List<Article> articles, List<String> userNames) {
        List<ArticleResponse> responses = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            responses.add(ArticleResponse.of(articles.get(i), userNames.get(i)));
        }
        return responses;
    }

    public static ArticleResponse of(Article article, String userName) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getContent(),
            userName);
    }
}
