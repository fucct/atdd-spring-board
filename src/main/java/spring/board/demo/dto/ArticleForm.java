package spring.board.demo.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import spring.board.demo.domain.Article;

@Getter @Setter
public class ArticleForm {

    @NotEmpty(message = "닉네임은 필수 항목입니다.")
    private String nickName;

    @NotEmpty(message = "패스워드는 필수 항목입니다.")
    private String password;

    @NotEmpty(message = "제목은 필수 항목입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

    public Article toArticle(){
        return Article.builder()
            .title(this.title)
            .content(this.content)
            .nickName(this.nickName)
            .password(this.password)
            .build();
    }
}
