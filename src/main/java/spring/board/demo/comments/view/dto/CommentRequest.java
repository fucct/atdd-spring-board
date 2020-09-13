package spring.board.demo.comments.view.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.comments.Comment;

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
