package spring.board.demo.domain.article;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comment.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("ARTICLE_COMMENT")
public class CommentRef {

    private Long comment;

    public static CommentRef of(Comment comment) {
        return new CommentRef(comment.getId());
    }

    public static Set<CommentRef> setOf(Set<Comment> comments) {
        return comments.stream().map(CommentRef::of).collect(Collectors.toSet());
    }
}
