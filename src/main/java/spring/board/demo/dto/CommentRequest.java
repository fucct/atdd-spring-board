package spring.board.demo.dto;

import javax.validation.constraints.NotBlank;

import spring.board.demo.domain.Comment;

public class CommentRequest {

    @NotBlank(message = "닉네임은 필수 입력 요소입니다.")
    private String userName;

    @NotBlank(message = "댓글 내용은 필수 입력 요소입니다.")
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public Comment toComment() {
        return Comment.of(this.userName, this.content);
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
