package spring.board.demo.domain.comments;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import spring.board.demo.domain.BaseTime;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.exception.AccessDeniedException;

@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
public class Comment extends BaseTime {

    @Id
    @With
    private final Long id;
    private final Long accountId;
    private final String content;

    public static Comment of(Long accountId, String content) {
        return Comment.builder()
            .id(null)
            .accountId(accountId)
            .content(content)
            .build();
    }

    public void validateUser(Account account) {
        account.validateId(accountId);
    }
}
