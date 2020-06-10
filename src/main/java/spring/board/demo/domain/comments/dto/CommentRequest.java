package spring.board.demo.domain.comments.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.comments.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank
    private String content;

    public Comment toComment(Long accountId) {
        return Comment.of(accountId, this.content);
    }
}
