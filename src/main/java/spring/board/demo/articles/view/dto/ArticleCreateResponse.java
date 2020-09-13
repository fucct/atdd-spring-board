package spring.board.demo.articles.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.articles.domain.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateResponse {
    private Long id;

    public static ArticleCreateResponse of(Article article) {
        return new ArticleCreateResponse(article.getId());
    }
}
