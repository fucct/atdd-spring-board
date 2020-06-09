package spring.board.demo.domain.users;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;

@Getter
@NoArgsConstructor
@Table("USER_ARTICLE")
public class ArticleRef {
    private Long article;

    public ArticleRef(Long article) {
        this.article = article;
    }

    public static ArticleRef of(Article article) {
        return new ArticleRef(article.getId());
    }

    public boolean hasSameId(Long id) {
        return article.equals(id);
    }
}
