package spring.board.demo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import spring.board.demo.domain.Article;

@Getter @Setter
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String nickName;
    private String content;
    private String createdDate;
    private List<CommentResponseDto> comments;

    public static ArticleResponseDto of(Article article) {
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getNickName(),
            article.getContent(),
            article.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            article.getComments().stream().map(CommentResponseDto::of).collect(Collectors.toList()));
    }
}
