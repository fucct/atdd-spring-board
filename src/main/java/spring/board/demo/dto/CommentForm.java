package spring.board.demo.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentForm {

    @NotEmpty(message = "닉네임은 필수 항목입니다.")
    private String commentNickname;

    @NotEmpty(message = "패스워드는 필수 항목입니다.")
    private String commentPassword;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String commentContent;
}
