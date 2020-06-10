package spring.board.demo.domain.comments;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.exception.AccessDeniedException;

@Getter
@NoArgsConstructor
@Table("COMMENTS")
public class Comment extends BaseTime {

    @Id
    private Long id;
    private Long accountId;
    private String content;

    @Builder
    public Comment(Long id, Long accountId, String content) {
        this.id = id;
        this.accountId = accountId;
        this.content = content;
    }

    public static Comment of(Long accountId, String content) {
        return Comment.builder()
            .id(null)
            .accountId(accountId)
            .content(content)
            .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void validateUser(Account account) {
        if (!account.isSameId(accountId)) {
            throw new AccessDeniedException();
        }
    }
}
