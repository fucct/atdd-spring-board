package spring.board.demo.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.dto.ArticleUpdateRequest;
import spring.board.demo.dto.CommentResponse;
import spring.board.demo.exception.CommentNotFoundException;

@NoArgsConstructor
@Getter
public class Article extends BaseTime {

    @Id
    private Long id;
    private String title;
    private String userName;
    private String content;
    private Set<Comment> comments;

    @Builder
    public Article(Long id, String title, String userName, String content, Set<Comment> comments) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.comments = comments;
    }

    public static Article of(Long id, String title, String userName, String content) {
        return new Article(id, title, userName, content, new LinkedHashSet<>());
    }

    public static Article of(String title, String userName, String content) {
        return new Article(null, title, userName, content, new LinkedHashSet<>());
    }

    public void update(ArticleUpdateRequest request) {
        if (Objects.nonNull(request.getTitle())) {
            this.title = request.getTitle();
        }
        if (Objects.nonNull(request.getContent())) {
            this.content = request.getContent();
        }
    }

    public List<Long> getCommentIds() {
        if (Objects.isNull(comments) || comments.size() == 0) {
            return new ArrayList<>();
        }
        return comments.stream().map(Comment::getId).collect(Collectors.toList());
    }

    public void addComment(Comment comment) {
        comment.setArticle(this.id);
        this.comments.add(comment);
    }

    public CommentResponse findComment(Long commentId) {
        Comment response = comments.stream()
            .filter(comment -> comment.getId().equals(commentId))
            .findAny()
            .orElseThrow(() -> new CommentNotFoundException(commentId));

        return CommentResponse.of(response);
    }

    public void updateComment(Long commentId, String content) {
        Comment targetComment = comments.stream().filter(comment -> comment.isEqualIdTo(commentId))
            .findFirst()
            .orElseThrow(() -> new CommentNotFoundException(commentId));
        targetComment.setContent(content);
    }

    public void deleteComment(Long commentId) {
        Comment targetComment = comments.stream().filter(comment -> comment.isEqualIdTo(commentId))
            .findFirst()
            .orElseThrow(() -> new CommentNotFoundException(commentId));
        comments.remove(targetComment);
    }
}
