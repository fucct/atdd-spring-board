package spring.board.demo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.board.demo.domain.Article;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String content;

    public Article toArticle() {
        return Article.builder()
            .title(this.title)
            .userName(this.userName)
            .content(this.content)
            .build();
    }
}
