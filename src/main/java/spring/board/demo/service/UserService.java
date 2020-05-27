package spring.board.demo.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.token.BearerTokenProvider;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.UserRepository;
import spring.board.demo.domain.user.dto.LoginRequest;
import spring.board.demo.domain.user.dto.UserCreateRequest;
import spring.board.demo.exception.NotFoundUserException;
import spring.board.demo.exception.NotMatchPasswordException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BearerTokenProvider tokenProvider;

    public UserService(UserRepository userRepository,
        BearerTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public Long create(UserCreateRequest request) {
        User save = userRepository.save(request.toUser());
        return save.getId();
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
            .orElseThrow(() -> new NotFoundUserException(request.getUserId()));
        validatePassword(request, user);
        Token token = tokenProvider.createToken(request.getUserId());
        return TokenResponse.of(token);
    }

    private void validatePassword(LoginRequest request, User user) {
        if (Objects.equals(user.getPassword(), request.getPassword())) {
            return;
        }
        throw new NotMatchPasswordException();
    }
}
