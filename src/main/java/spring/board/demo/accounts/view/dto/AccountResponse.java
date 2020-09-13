package spring.board.demo.accounts.view.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.accounts.domain.Account;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String email;
    private String userName;

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(), account.getEmail(), account.getName());
    }

    public static List<AccountResponse> listOf(List<Account> accounts) {
        return accounts.stream().map(AccountResponse::of).collect(Collectors.toList());
    }
}
