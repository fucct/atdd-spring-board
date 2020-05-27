package spring.board.demo.domain;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table("user_article")
@NoArgsConstructor
public class ArticleRef {
    private Long article;

    public ArticleRef(Long article) {
        this.article = article;
    }
}
