package spring.board.demo.domain.articles.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.articles.Article;

@Getter
@NoArgsConstructor
public class ArticlePreviewResponse {
    private Long id;
    private Long accountId;
    private String title;
    private String content;
    private String accountName;

    @Builder
    public ArticlePreviewResponse(Long id, Long accountId, String title, String content,
        String accountName) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.content = content;
        this.accountName = accountName;
    }

    public static ArticlePreviewResponse of(Article article, String accountName) {
        return ArticlePreviewResponse.builder()
            .id(article.getId())
            .accountId(article.getAccountId())
            .title(article.getTitle())
            .content(article.getContent())
            .accountName(accountName)
            .build();
    }
}
