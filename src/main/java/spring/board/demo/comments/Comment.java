package spring.board.demo.comments;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.With;
import spring.board.demo.common.BaseTime;
import spring.board.demo.accounts.domain.Account;

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
