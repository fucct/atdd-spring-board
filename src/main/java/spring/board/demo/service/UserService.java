package spring.board.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.token.BearerTokenProvider;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.UserRepository;
import spring.board.demo.domain.user.dto.LoginRequest;
import spring.board.demo.domain.user.dto.UserCreateRequest;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserUpdateRequest;
import spring.board.demo.exception.UserNotFoundException;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BearerTokenProvider tokenProvider;

    public UserService(UserRepository userRepository,
        BearerTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public UserCreateResponse create(UserCreateRequest request) {
        User save = userRepository.save(request.toUser());
        return new UserCreateResponse(save.getId());
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        User user = findByUserId(request.getUserId())
            .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        user.checkPassword(request.getPassword());
        Token token = tokenProvider.createToken(request.getUserId());
        return TokenResponse.of(token);
    }

    public void update(Long id, User user, UserUpdateRequest request) {
        user.update(id, request);
        userRepository.save(user);
    }

    public void delete(User user, Long id) {
        user.validateId(id);
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
