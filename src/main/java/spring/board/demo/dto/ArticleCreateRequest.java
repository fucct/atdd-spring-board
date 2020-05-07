package spring.board.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleCreateRequest {

    private String title;
    private String userName;
    private String content;
}
