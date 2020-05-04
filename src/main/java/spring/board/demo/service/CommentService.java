package spring.board.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.board.demo.domain.Article;
import spring.board.demo.domain.Comment;
import spring.board.demo.repository.ArticleRepository;
import spring.board.demo.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Long addComment(Long articleId, String content, String nickName, String password){
        Article article = articleRepository.findById(articleId);
        Comment comment = Comment.createComment(article, content, nickName, password);
        commentRepository.save(comment);
        return comment.getId();
    }

}
