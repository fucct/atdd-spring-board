package spring.board.demo.domain.articles;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.domain.comments.Comment;

@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Article extends BaseTime {

    @Id @With
    private final Long id;
    private final Long accountId;
    private final String title;
    private final String content;
    private final Set<CommentRef> comments = new LinkedHashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(CommentRef.of(comment));
    }
}
