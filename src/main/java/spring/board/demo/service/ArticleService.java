package spring.board.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.dto.ArticleDetailResponse;
import spring.board.demo.domain.article.dto.ArticleRequest;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.comment.dto.CommentRequest;
import spring.board.demo.domain.users.User;
import spring.board.demo.exception.ArticleNotFoundException;

@Service
@Transactional
public class ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;

    public ArticleService(UserService userService,
        ArticleRepository articleRepository) {
        this.userService = userService;
        this.articleRepository = articleRepository;
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
        return ArticleDetailResponse.of(article);
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

    public ArticleDetailResponse addComment(Long articleId, Long userId, CommentRequest request) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new ArticleNotFoundException(articleId));
        Comment comment = request.toComment(userId);
        article.addComment(comment);
        Article persistArticle = articleRepository.save(article);
        return ArticleDetailResponse.of(persistArticle);
    }
}
