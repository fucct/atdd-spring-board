package spring.board.demo.service;

import static org.mockito.Mockito.*;
import static spring.board.demo.acceptance.AcceptanceTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.UserRepository;
import spring.board.demo.infra.BearerTokenProvider;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BearerTokenProvider tokenProvider;

    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.of(1L, TEST_USER_ID, TEST_USER_NAME, TEST_USER_PASSWORD);
        userService = new UserService(userRepository, tokenProvider);

    }



    @Test
    void create() {
        when(userRepository.save(any())).thenReturn(user);


    }
}