package spring.board.demo.articles.view.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.comments.view.dto.CommentDetailResponse;

@NoArgsConstructor
@Getter
public class ArticleDetailResponse {

    private Long id;
    private Long accountId;
    private String title;
    private String content;
    private String accountName;
    private List<CommentDetailResponse> comments;
}
