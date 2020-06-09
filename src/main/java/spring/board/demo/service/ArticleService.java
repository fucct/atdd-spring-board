package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.CommentRef;
import spring.board.demo.domain.article.dto.ArticleDetailResponse;
import spring.board.demo.domain.article.dto.ArticleRequest;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.comment.CommentRepository;
import spring.board.demo.domain.comment.dto.CommentDetailResponse;
import spring.board.demo.domain.comment.dto.CommentRequest;
import spring.board.demo.domain.comment.dto.CommentResponse;
import spring.board.demo.domain.users.User;
import spring.board.demo.exception.ArticleNotFoundException;
import spring.board.demo.exception.CommentNotFoundException;

@Service
@Transactional
public class ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleService(UserService userService,
        ArticleRepository articleRepository,
        CommentRepository commentRepository) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public ArticleResponse save(User user, ArticleRequest request) {
        Article article = request.toArticle(user);
        Article persistArticle = articleRepository.save(article);
        user.addArticle(persistArticle);
        userService.save(user);
        return ArticleResponse.of(persistArticle);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return ArticleResponse.listOf(articles);
    }

    public ArticleDetailResponse getArticle(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ArticleNotFoundException(id));
        List<Long> commentIds = article.getComments()
            .stream()
            .map(CommentRef::getComment)
            .collect(Collectors.toList());
        List<CommentDetailResponse> detailResponses = commentRepository.findCommentsByIds(
            commentIds);
        return ArticleDetailResponse.of(article, detailResponses);
    }

    public void update(Long id, User user, ArticleRequest request) {
        user.validateArticle(id);
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new ArticleNotFoundException(id));
        article.update(request);
        articleRepository.save(article);
    }

    public void delete(Long id, User user) {
        articleRepository.deleteById(id);
        user.deleteArticle(id);
        userService.save(user);
    }

    public CommentResponse addComment(Long articleId, Long userId, CommentRequest request) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new ArticleNotFoundException(articleId));
        Comment comment = request.toComment(userId);
        Comment persistComment = commentRepository.save(comment);
        article.addComment(persistComment);
        articleRepository.save(article);
        return CommentResponse.of(persistComment);
    }

    public CommentDetailResponse getComment(Long id) {
        return commentRepository.findCommentById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
    }
}
