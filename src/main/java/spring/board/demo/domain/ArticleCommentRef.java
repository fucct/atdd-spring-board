package spring.board.demo.domain;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Table("article_comment")
@Getter
@NoArgsConstructor
public class ArticleCommentRef {
    private Long comment;

    public ArticleCommentRef(Long comment) {
        this.comment = comment;
    }
}
