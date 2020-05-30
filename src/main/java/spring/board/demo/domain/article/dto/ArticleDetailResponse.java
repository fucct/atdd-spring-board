package spring.board.demo.domain.article.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private List<Comment> comments;

    public static ArticleDetailResponse of(Article article, List<Comment> comments) {
        return new ArticleDetailResponse(article.getId(), article.getTitle(),
            article.getUserRef().getUserName(), article.getContent(), comments);
    }
}
