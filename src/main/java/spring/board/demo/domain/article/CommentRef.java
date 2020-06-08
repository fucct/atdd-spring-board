package spring.board.demo.domain.article;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comment.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("article_comment")
public class CommentRef {

    private Long comment;

    public static CommentRef of(Comment comment) {
        return new CommentRef(comment.getId());
    }
}
