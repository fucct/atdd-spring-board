package spring.board.demo.domain.articles.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;

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
