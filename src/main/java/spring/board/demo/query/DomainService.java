package spring.board.demo.query;

import org.springframework.stereotype.Service;

import spring.board.demo.accounts.domain.AccountRepository;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.comments.CommentRepository;

@Service
public class DomainService {
    private final AccountRepository accountRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public DomainService(AccountRepository accountRepository,
        ArticleRepository articleRepository,
        CommentRepository commentRepository) {
        this.accountRepository = accountRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }


}
