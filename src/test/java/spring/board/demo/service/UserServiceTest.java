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

import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.comment.CommentRepository;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.users.User;
import spring.board.demo.domain.users.UserRepository;
import spring.board.demo.domain.users.dto.LoginRequest;
import spring.board.demo.domain.users.dto.UserCreateRequest;
import spring.board.demo.domain.users.dto.UserCreateResponse;
import spring.board.demo.domain.users.dto.UserUpdateRequest;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CommentRepository commentRepository;

    private UserService userService;

    private User user;
    private Token token;

    @BeforeEach
    void setUp() {
        user = User.of(TEST_ID, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        token = Token.builder()
            .accessToken(TEST_USER_TOKEN)
            .refreshToken(TEST_OTHER_USER_TOKEN)
            .tokenType("bearer")
            .build();
        userService = new UserService(userRepository, articleRepository, commentRepository,
            tokenProvider);
    }

    @Test
    @DisplayName("유저를 생성")
    void create() {
        when(userRepository.save(any())).thenReturn(user);
        UserCreateResponse response = userService.create(
            new UserCreateRequest(TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD));
        assertThat(response.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저를 저장")
    void save() {
        when(userRepository.save(any())).thenReturn(user);
        assertThat(userService.save(user)).isEqualToComparingFieldByField(user);
    }

    @Test
    @DisplayName("로그인시 토큰 반환")
    void login() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        when(tokenProvider.createToken(any())).thenReturn(token);
        TokenResponse tokenResponse = userService.login(
            LoginRequest.builder().userId(TEST_USER_ID).password(TEST_USER_PASSWORD).build());
        assertThat(tokenResponse).isEqualToComparingFieldByField(token);
    }

    @Test
    @DisplayName("업데이트 시 정보 변경")
    void update() {
        userService.update(TEST_ID, user, UserUpdateRequest.builder()
            .name(TEST_OTHER_USER_NAME)
            .oldPassword(TEST_USER_PASSWORD)
            .newPassword(TEST_OTHER_USER_PASSWORD)
            .build());
        assertThat(user)
            .hasFieldOrPropertyWithValue("userId", TEST_USER_ID)
            .hasFieldOrPropertyWithValue("name", TEST_OTHER_USER_NAME)
            .hasFieldOrPropertyWithValue("password", TEST_OTHER_USER_PASSWORD);
    }
}