package spring.board.demo.service;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.comments.Comment;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.domain.comments.dto.CommentRequest;
import spring.board.demo.domain.comments.dto.CommentResponse;
import spring.board.demo.exception.CommentNotFoundException;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void updateComment(Long id, CommentRequest commentRequest, Account account) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
        comment.validateUser(account);
        comment.updateContent(commentRequest.getContent());
        commentRepository.save(comment);
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
        Comment comment = request.toComment(articleId, account.getId());
        commentRepository.save(comment);
        return CommentResponse.of(comment);
    }
}
