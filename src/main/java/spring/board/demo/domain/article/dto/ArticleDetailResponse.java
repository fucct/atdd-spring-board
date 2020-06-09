package spring.board.demo.domain.article.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.comment.dto.CommentDetailResponse;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private List<CommentDetailResponse> comments;

    public static ArticleDetailResponse of(Article article, List<CommentDetailResponse> comments) {
        return new ArticleDetailResponse(article.getId(), article.getTitle(), article.getContent(),
            article.getUserName(), comments);
    }

    public static ArticleDetailResponse of(Article article) {
        return new ArticleDetailResponse(article.getId(), article.getTitle(), article.getContent(),
            article.getUserName(), new ArrayList<>());
    }

}
