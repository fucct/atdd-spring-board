package spring.board.demo.domain.comment.dto;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comment.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;

    private Long userId;

    private String content;

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getUserId(), comment.getContent());
    }

    public static Set<CommentResponse> setOf(Set<Comment> comments) {
        return comments.stream().map(CommentResponse::of).collect(Collectors.toSet());
    }
}
