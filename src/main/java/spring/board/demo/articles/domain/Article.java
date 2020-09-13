package spring.board.demo.articles.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;
import spring.board.demo.common.BaseTime;
import spring.board.demo.comments.Comment;

@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Article extends BaseTime {

    @Id @With
    private final Long id;
    private final String title;
    private final String content;
    private final Set<CommentRef> comments = new LinkedHashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(CommentRef.of(comment));
    }
}
