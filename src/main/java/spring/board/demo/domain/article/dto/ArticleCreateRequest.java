package spring.board.demo.domain.article.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;

@Getter
@NoArgsConstructor
public class ArticleCreateRequest {

    @NotBlank(message = "제목은 필수 입력 요소입니다.")
    private String title;

    @NotBlank(message = "닉네임은 필수 입력 요소입니다.")
    private String userName;

    @NotBlank(message = "내용은 필수 입력 요소입니다.")
    private String content;

    @Builder
    public ArticleCreateRequest(String title, String userName, String content) {
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    public Article toArticle() {
        return Article.of(title, userName, content);
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
