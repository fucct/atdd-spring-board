package spring.board.demo.domain.articles.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.articles.CommentRef;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private Long accountId;
    private String accountName;
    private String title;
    private String content;
    private Set<CommentRef> comments;
}
