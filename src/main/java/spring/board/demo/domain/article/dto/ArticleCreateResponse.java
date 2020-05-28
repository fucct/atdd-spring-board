package spring.board.demo.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleCreateResponse {
    private Long id;
    private String title;
    private String content;

    public static ArticleCreateResponse of(Article article) {
        return new ArticleCreateResponse(article.getId(), article.getTitle(), article.getContent());
    }
}
