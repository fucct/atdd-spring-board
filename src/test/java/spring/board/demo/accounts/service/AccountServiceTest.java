package spring.board.demo.accounts.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.accounts.service.AccountService;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.accounts.view.dto.AccountCreateRequest;
import spring.board.demo.accounts.view.dto.AccountCreateResponse;
import spring.board.demo.accounts.view.dto.AccountUpdateRequest;
import spring.board.demo.accounts.view.dto.LoginRequest;
import spring.board.demo.articles.ArticleFixture;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.CommentRepository;
import spring.board.demo.infra.Token;
import spring.board.demo.infra.TokenProvider;
import spring.board.demo.infra.dto.TokenResponse;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CommentRepository commentRepository;

    private AccountService accountService;

    private Account account;
    private Token token;

    @BeforeEach
    void setUp() {
        account = Account.of(ArticleFixture.ID1, AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1);
        token = Token.builder()
            .accessToken(AccountFixture.TOKEN1)
            .refreshToken(AccountFixture.TOKEN2)
            .tokenType("bearer")
            .build();
        accountService = new AccountService(accountRepository, tokenProvider);
    }

    @Test
    @DisplayName("유저를 생성")
    void create() {
        when(accountRepository.save(any())).thenReturn(account);
        AccountCreateResponse response = accountService.create(
            new AccountCreateRequest(AccountFixture.EMAIL1, AccountFixture.NAME1, AccountFixture.PASSWORD1));
        assertThat(response.getId()).isEqualTo(account.getId());
    }

    @Test
    @DisplayName("유저를 저장")
    void save() {
        when(accountRepository.save(any())).thenReturn(account);
        assertThat(accountService.save(account)).isEqualToComparingFieldByField(account);
    }

    @Test
    @DisplayName("로그인시 토큰 반환")
    void login() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        when(tokenProvider.createToken(any())).thenReturn(token);
        TokenResponse tokenResponse = accountService.login(
            LoginRequest.builder()
                .email(AccountFixture.EMAIL1)
                .password(AccountFixture.PASSWORD1)
                .build());
        assertThat(tokenResponse).isEqualToComparingFieldByField(token);
    }

    @Test
    @DisplayName("업데이트 시 정보 변경")
    void update() {
        accountService.update(ArticleFixture.ID1, account, AccountUpdateRequest.builder()
            .name(AccountFixture.NAME2)
            .oldPassword(AccountFixture.PASSWORD1)
            .newPassword(AccountFixture.PASSWORD2)
            .build());
        assertThat(account)
            .hasFieldOrPropertyWithValue("email", AccountFixture.EMAIL1)
            .hasFieldOrPropertyWithValue("name", AccountFixture.NAME2)
            .hasFieldOrPropertyWithValue("password", AccountFixture.PASSWORD2);
    }
}
