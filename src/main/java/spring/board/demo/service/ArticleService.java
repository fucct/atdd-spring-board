package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.Article;
import spring.board.demo.domain.Comment;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleDetailResponse;
import spring.board.demo.dto.ArticleResponse;
import spring.board.demo.dto.ArticleUpdateRequest;
import spring.board.demo.dto.CommentRequest;
import spring.board.demo.exception.IdNotFoundException;
import spring.board.demo.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    public ArticleService(ArticleRepository articleRepository,
        CommentService commentService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    public Long save(ArticleCreateRequest request) {
        Article persistArticle = articleRepository.save(request.toArticle());

        return persistArticle.getId();
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll().stream()
            .map(ArticleResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));

        return ArticleResponse.of(article);
    }

    public void updateById(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));
        article.update(request);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleDetailResponse getDetailArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));
        List<Comment> comments = commentService.findAllById(article.getCommentIds());
        return ArticleDetailResponse.of(article, comments);
    }

    public Long addComment(Long id, CommentRequest commentRequest) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));
        Comment comment = commentRequest.toComment();
        article.addComment(comment);
        articleRepository.save(article);

        return comment.getId();
    }
}
