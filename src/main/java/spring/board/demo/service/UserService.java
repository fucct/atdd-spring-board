package spring.board.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.comment.CommentRepository;
import spring.board.demo.domain.comment.dto.CommentResponse;
import spring.board.demo.domain.token.Token;
import spring.board.demo.domain.token.TokenProvider;
import spring.board.demo.domain.token.dto.TokenResponse;
import spring.board.demo.domain.users.ArticleRef;
import spring.board.demo.domain.users.User;
import spring.board.demo.domain.users.UserRepository;
import spring.board.demo.domain.users.dto.LoginRequest;
import spring.board.demo.domain.users.dto.UserCreateRequest;
import spring.board.demo.domain.users.dto.UserCreateResponse;
import spring.board.demo.domain.users.dto.UserDetailResponse;
import spring.board.demo.domain.users.dto.UserUpdateRequest;
import spring.board.demo.exception.UserNotFoundException;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository,
        ArticleRepository articleRepository,
        CommentRepository commentRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
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

    public UserDetailResponse getDetailUser(User user) {
        Set<ArticleRef> articleRefs = user.getArticles();
        List<Article> articles = articleRepository.findAllById(
            articleRefs.stream().map(ArticleRef::getArticle).collect(
                Collectors.toList()));
        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        return UserDetailResponse.of(user, ArticleResponse.listOf(articles),
            CommentResponse.listOf(comments));
    }
}
