package spring.board.demo.service;

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

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.accounts.dto.AccountCreateRequest;
import spring.board.demo.domain.accounts.dto.AccountCreateResponse;
import spring.board.demo.domain.accounts.dto.AccountUpdateRequest;
import spring.board.demo.domain.accounts.dto.LoginRequest;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.domain.token.dto.TokenResponse;

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
        account = Account.of(TEST_ID, TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD);
        token = Token.builder()
            .accessToken(TEST_ACCOUNT_TOKEN)
            .refreshToken(TEST_OTHER_ACCOUNT_TOKEN)
            .tokenType("bearer")
            .build();
        accountService = new AccountService(accountRepository, tokenProvider);
    }

    @Test
    @DisplayName("유저를 생성")
    void create() {
        when(accountRepository.save(any())).thenReturn(account);
        AccountCreateResponse response = accountService.create(
            new AccountCreateRequest(TEST_ACCOUNT_EMAIL, TEST_ACCOUNT_NAME, TEST_ACCOUNT_PASSWORD));
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
                .email(TEST_ACCOUNT_EMAIL)
                .password(TEST_ACCOUNT_PASSWORD)
                .build());
        assertThat(tokenResponse).isEqualToComparingFieldByField(token);
    }

    @Test
    @DisplayName("업데이트 시 정보 변경")
    void update() {
        accountService.update(TEST_ID, account, AccountUpdateRequest.builder()
            .name(TEST_OTHER_ACCOUNT_NAME)
            .oldPassword(TEST_ACCOUNT_PASSWORD)
            .newPassword(TEST_OTHER_ACCOUNT_PASSWORD)
            .build());
        assertThat(account)
            .hasFieldOrPropertyWithValue("email", TEST_ACCOUNT_EMAIL)
            .hasFieldOrPropertyWithValue("name", TEST_OTHER_ACCOUNT_NAME)
            .hasFieldOrPropertyWithValue("password", TEST_OTHER_ACCOUNT_PASSWORD);
    }
}