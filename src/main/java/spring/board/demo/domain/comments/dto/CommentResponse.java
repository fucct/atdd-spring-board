package spring.board.demo.domain.comments.dto;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comments.Comment;

@Getter
@NoArgsConstructor
public class CommentResponse {
    @Column("comment_id")
    private Long id;
    private Long accountId;
    @Column("comment_content")
    private String content;

    @Builder
    public CommentResponse(Long id, Long accountId, String content) {
        this.id = id;
        this.accountId = accountId;
        this.content = content;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .accountId(comment.getAccountId())
            .content(comment.getContent())
            .build();
    }
}
