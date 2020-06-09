package spring.board.demo.service;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.comment.CommentRepository;
import spring.board.demo.domain.comment.dto.CommentRequest;
import spring.board.demo.domain.users.User;
import spring.board.demo.exception.CommentNotFoundException;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void updateComment(Long id, CommentRequest commentRequest, User user) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
        comment.validateUser(user);
        comment.updateContent(commentRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CommentNotFoundException(id));
        comment.validateUser(user);
        commentRepository.delete(comment);
    }
}
