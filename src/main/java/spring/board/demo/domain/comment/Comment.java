package spring.board.demo.domain.comment;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;

@Table(value = "comments")
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {

    @Id
    private Long id;

    private String content;

    public static Comment of(String content) {
        return Comment.builder()
            .id(null)
            .content(content)
            .build();
    }

    public static Comment of(Long id, String content) {
        return Comment.builder()
            .id(id)
            .content(content)
            .build();
    }

    @Builder
    public Comment(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEqualIdTo(Long id) {
        return Objects.equals(this.id, id);
    }

    public static class CommentRequest {

        @NotBlank(message = "닉네임은 필수 입력 요소입니다.")
        private String userName;

        @NotBlank(message = "댓글 내용은 필수 입력 요소입니다.")
        private String content;

        public CommentRequest() {
        }

        public CommentRequest(String userName, String content) {
            this.userName = userName;
            this.content = content;
        }

        public Comment toComment() {
            return of(this.content);
        }

        public String getUserName() {
            return userName;
        }

        public String getContent() {
            return content;
        }
    }

    @NoArgsConstructor
    public static class CommentResponse {

        private Long id;
        private String content;

        public static CommentResponse of(Comment comment) {
            return new CommentResponse(comment.getId(), comment.getContent());
        }

        public CommentResponse(Long id, String content) {
            this.id = id;
            this.content = content;
        }

        public Long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }
}
