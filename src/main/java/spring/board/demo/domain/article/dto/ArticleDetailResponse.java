package spring.board.demo.domain.article.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.comment.dto.CommentResponse;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private Set<CommentResponse> comments;

    public static ArticleDetailResponse of(Article article) {
        return new ArticleDetailResponse(article.getId(), article.getTitle(),
            article.getUserRef().getUserName(), article.getContent(),
            CommentResponse.setOf(article.getComments()));
    }
}
