package spring.board.demo.domain.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.users.User;
import spring.board.demo.exception.AccessDeniedException;

@Table("COMMENTS")
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {

    @Id
    private Long id;

    private Long userId;

    private String content;

    @Builder
    public Comment(Long id, Long userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
    }

    public static Comment of(Long userId, String content) {
        return Comment.builder()
            .id(null)
            .userId(userId)
            .content(content)
            .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void validateUser(User user) {
        if (!user.isSameId(userId)) {
            throw new AccessDeniedException();
        }
    }
}
