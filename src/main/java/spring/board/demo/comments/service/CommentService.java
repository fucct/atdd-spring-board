package spring.board.demo.comments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.Article;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.Comment;
import spring.board.demo.comments.CommentRepository;
import spring.board.demo.comments.view.dto.CommentDetailResponse;
import spring.board.demo.comments.view.dto.CommentRequest;
import spring.board.demo.comments.view.dto.CommentResponse;
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
