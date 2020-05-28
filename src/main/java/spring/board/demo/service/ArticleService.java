package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.ArticleRef;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.dto.ArticleCreateRequest;
import spring.board.demo.domain.article.dto.ArticleCreateResponse;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.user.User;

@Service
@Slf4j
@Transactional
public class ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    public ArticleService(UserService userService,
        ArticleRepository articleRepository, CommentService commentService) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    public ArticleCreateResponse save(User user, ArticleCreateRequest request) {
        Article article = request.toArticle();
        Article persistArticle = articleRepository.save(article);
        user.addArticle(article);
        userService.save(user);
        return ArticleCreateResponse.of(persistArticle);
    }

    public List<ArticleResponse> getArticles(User user) {
        List<Long> articleIds = user.getArticles()
            .stream()
            .map(ArticleRef::getArticle)
            .collect(Collectors.toList());
        List<Article> articles = articleRepository.findAllById(articleIds);
        return ArticleResponse.listOf(articles, user.getName());
    }
    //
    // @Transactional(readOnly = true)
    // public List<ArticleResponse> getArticles() {
    //     List<Article> articles = articleRepository.findAll();
    //     return ArticleResponse.listOf(articles);
    // }
    //
    // @Transactional(readOnly = true)
    // public ArticleResponse getArticleById(Long id) {
    //     Article article = articleRepository.findById(id)
    //         .orElseThrow(() -> new ArticleNotFoundException(id));
    //
    //     return ArticleResponse.of(article);
    // }
    //
    // public void updateById(Long id, ArticleUpdateRequest request) {
    //     Article article = articleRepository.findById(id)
    //         .orElseThrow(() -> new ArticleNotFoundException(id));
    //     article.update(request);
    //     articleRepository.save(article);
    // }
    //
    // public void deleteById(Long id) {
    //     articleRepository.deleteById(id);
    // }
    //
    // public ArticleDetailResponse getDetailArticleById(Long id) {
    //     Article article = articleRepository.findById(id)
    //         .orElseThrow(() -> new ArticleNotFoundException(id));
    //     List<Comment> comments = commentService.findAllById(article.getCommentIds());
    //     return ArticleDetailResponse.of(article, comments);
    // }
    //
    // public Long addComment(Long id, Comment.CommentRequest commentRequest) {
    //     Article article = articleRepository.findById(id)
    //         .orElseThrow(() -> new ArticleNotFoundException(id));
    //     Comment comment = commentRequest.toComment();
    //     article.addComment(comment);
    //     articleRepository.save(article);
    //
    //     return comment.getId();
    // }
    //
    // public Comment.CommentResponse getComment(Long articleId, Long commentId) {
    //     Article article = articleRepository.findById(articleId)
    //         .orElseThrow(() -> new ArticleNotFoundException
    //             (articleId));
    //
    //     return article.findComment(commentId);
    // }
    //
    // public void updateCommentById(Long articleId, Long commentId, String content) {
    //     Article article = articleRepository.findById(articleId)
    //         .orElseThrow(() -> new ArticleNotFoundException(articleId));
    //     article.updateComment(commentId, content);
    //     articleRepository.save(article);
    // }
    //
    // public void deleteCommentById(Long articleId, Long commentId) {
    //     Article article = articleRepository.findById(articleId)
    //         .orElseThrow(() -> new ArticleNotFoundException(articleId));
    //     article.deleteComment(commentId);
    //     articleRepository.save(article);
    // }
}
