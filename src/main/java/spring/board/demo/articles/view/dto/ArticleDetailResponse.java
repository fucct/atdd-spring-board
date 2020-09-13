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

    @Builder
    public ArticleDetailResponse(Long id, Long accountId, String title, String content,
        String accountName, List<CommentDetailResponse> comments) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.content = content;
        this.accountName = accountName;
        this.comments = comments;
    }

    public static ArticleDetailResponse of(Article article, Account account,
        List<CommentDetailResponse> comments) {
        return ArticleDetailResponse.builder()
            .id(article.getId())
            .accountId(article.getAccountId())
            .title(article.getTitle())
            .content(article.getContent())
            .accountName(account.getName())
            .comments(comments)
            .build();
    }
}
