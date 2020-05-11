package spring.board.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.board.demo.domain.Article;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

    private Long id;
    private String title;
    private String userName;
    private String content;

    public static ArticleResponse of(Article article) {
        return new ArticleResponse(article.getId(), article.getTitle(), article.getUserName(),
            article.getContent());
    }
}
