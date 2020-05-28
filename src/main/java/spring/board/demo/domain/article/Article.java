package spring.board.demo.domain.article;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.ArticleCommentRef;
import spring.board.demo.domain.BaseTime;

@NoArgsConstructor
@Getter
public class Article extends BaseTime {

    @Id
    private Long id;
    private String title;
    private String content;
    private Set<ArticleCommentRef> comments = new LinkedHashSet<>();

    @Builder
    public Article(Long id, String title, String content, Set<ArticleCommentRef> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

    public static Article of(Long id, String title, String content) {
        return Article.builder()
            .id(id)
            .title(title)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    public static Article of(String title, String content) {
        return Article.builder()
            .id(null)
            .title(title)
            .content(content)
            .comments(new LinkedHashSet<>())
            .build();
    }

    // public void update(ArticleUpdateRequest request) {
    //     if (Objects.nonNull(request.getTitle())) {
    //         this.title = request.getTitle();
    //     }
    //     if (Objects.nonNull(request.getContent())) {
    //         this.content = request.getContent();
    //     }
    // }
    //
    // public List<Long> getCommentIds() {
    //     if (Objects.isNull(comments) || comments.size() == 0) {
    //         return new ArrayList<>();
    //     }
    //     return comments.stream().map(Comment::getId).collect(Collectors.toList());
    // }
    //
    // public void addComment(Comment comment) {
    //     this.comments.add(new ArticleCommentRef(comment.getId()));
    // }
}
