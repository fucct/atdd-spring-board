package spring.board.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.comments.Comment;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.domain.comments.dto.CommentRequest;
import spring.board.demo.domain.comments.dto.CommentResponse;
import spring.board.demo.exception.ArticleNotFoundException;
import spring.board.demo.exception.CommentNotFoundException;

@Service
@Transactional
public class CommentService {

    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;

    public CommentService(ArticleRepository articleRepository,
        CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public void updateComment(Long id, CommentRequest commentRequest, Account account) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
        comment.validateUser(account);
    }

    public void deleteComment(Long id, Account account) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
        comment.validateUser(account);
        commentRepository.delete(comment);
    }

    public CommentDetailResponse getComment(Long id) {
        return commentRepository.findCommentById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public CommentResponse addComment(Long articleId, Account account, CommentRequest request) {
        Comment comment = request.toComment(account.getId());
        commentRepository.save(comment);
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new ArticleNotFoundException(articleId));
        article.addComment(comment);
        articleRepository.save(article);
        return CommentResponse.of(comment);
    }
}
