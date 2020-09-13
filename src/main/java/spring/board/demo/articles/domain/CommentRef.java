package spring.board.demo.articles.domain;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import spring.board.demo.comments.Comment;

@Builder
@Getter
@Table("ARTICLE_COMMENT")
public class CommentRef {
    private final Long comment;

    public static CommentRef of(Comment comment) {
        return new CommentRef(comment.getId());
    }

    public static Set<CommentRef> setOf(Set<Comment> comments) {
        return comments.stream().map(CommentRef::of).collect(Collectors.toSet());
    }
}
