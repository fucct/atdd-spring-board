package spring.board.demo.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comment.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank
    private String content;

    public Comment toComment(Long userId) {
        return Comment.of(userId, this.content);
    }
}
