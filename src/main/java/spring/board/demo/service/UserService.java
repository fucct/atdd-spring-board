package spring.board.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.token.BearerTokenProvider;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.user.User;
import spring.board.demo.domain.user.UserRepository;
import spring.board.demo.domain.user.dto.LoginRequest;
import spring.board.demo.domain.user.dto.UserCreateRequest;
import spring.board.demo.domain.user.dto.UserCreateResponse;
import spring.board.demo.domain.user.dto.UserResponse;
import spring.board.demo.domain.user.dto.UserUpdateRequest;
import spring.board.demo.exception.NotFoundUserException;

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

    public TokenResponse login(LoginRequest request) {
        User user = findByUserId(request.getUserId())
            .orElseThrow(() -> new NotFoundUserException(request.getUserId()));
        user.checkPassword(request.getPassword());
        Token token = tokenProvider.createToken(request.getUserId());
        return TokenResponse.of(token);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundUserException(request.getName()));
        user.checkPassword(request.getOldPassword());
        user.update(request);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return UserResponse.listOf(users);
    }
}
