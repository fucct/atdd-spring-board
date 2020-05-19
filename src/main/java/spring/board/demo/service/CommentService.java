package spring.board.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import spring.board.demo.domain.Comment;
import spring.board.demo.repository.CommentRepository;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAllById(List<Long> commentIds) {
        return commentRepository.findAllById(commentIds);
    }


}
