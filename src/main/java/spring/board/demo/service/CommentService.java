package spring.board.demo.service;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.comment.CommentRepository;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
