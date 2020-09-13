package spring.board.demo.articles.view.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.articles.domain.CommentRef;

@Getter
@NoArgsConstructor
public class ArticleResponse {
    private Long id;
    private Long accountId;
    private String accountName;
    private String title;
    private String content;
    private Set<CommentRef> comments;

    @Builder
    public ArticleResponse(Long id, Long accountId, String accountName, String title,
        String content, Set<CommentRef> comments) {
        this.id = id;
        this.accountId = accountId;
        this.accountName = accountName;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    public static ArticleResponse of(Article article, String accountName) {
        return ArticleResponse.builder()
            .id(article.getId())
            .accountId(article.getAccountId())
            .accountName(accountName)
            .title(article.getTitle())
            .content(article.getContent())
            .comments(article.getComments())
            .build();
    }

}
