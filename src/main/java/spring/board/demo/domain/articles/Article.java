package spring.board.demo.domain.articles;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.domain.comments.Comment;

@NoArgsConstructor
@Getter
public class Article extends BaseTime {

    @Id
    private Long id;
    private Long accountId;
    private String title;
    private String content;
    private Set<CommentRef> comments;

    @Builder
    public Article(Long id, Long accountId, String title, String content,
        Set<CommentRef> comments) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    public static Article of(Long id, Long accountId, String title, String content) {
        return Article.builder()
            .id(id)
            .title(title)
            .accountId(accountId)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public static Article of(Long accountId, String title, String content) {
        return Article.builder()
            .title(title)
            .accountId(accountId)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public static Article of(Long id, Account account, String title, String content) {
        return Article.builder()
            .id(id)
            .accountId(account.getId())
            .title(title)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public static Article of(Account account, String title, String content) {
        return Article.builder()
            .id(null)
            .accountId(account.getId())
            .title(title)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public void addComment(Comment comment) {
        this.comments.add(CommentRef.of(comment));
    }
}
