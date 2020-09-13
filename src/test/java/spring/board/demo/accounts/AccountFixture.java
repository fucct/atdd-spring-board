package spring.board.demo.accounts;

import spring.board.demo.accounts.domain.Account;

public class AccountFixture {
    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final String EMAIL1 = "dqrd123@gmail.com";
    public static final String EMAIL2 = "fucct@naver.com";
    public static final String NAME1 = "TAEHEON KIM";
    public static final String NAME2 = "Honey Bee";
    public static final String PASSWORD1 = "1234";
    public static final String PASSWORD2 = "5678";

    public static Account createWithoutId() {
        return Account.builder()
            .email(EMAIL1)
            .name(NAME1)
            .password(PASSWORD1)
            .build();
    }

    public static Account createWithId(Long id) {
        return Account.builder()
            .id(id)
            .email(EMAIL1)
            .name(NAME1)
            .password(PASSWORD1)
            .build();
    }
}
