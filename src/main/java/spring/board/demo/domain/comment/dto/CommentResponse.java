package spring.board.demo.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comment.Comment;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private Long userId;
    private String content;

    @Builder
    public CommentResponse(Long id, Long userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .userId(comment.getUserId())
            .content(comment.getContent())
            .build();
    }
}
