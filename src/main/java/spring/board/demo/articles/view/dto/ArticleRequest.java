package spring.board.demo.articles.view.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {

    @NotBlank(message = "제목은 필수 입력 요소입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 요소입니다.")
    private String content;
}
