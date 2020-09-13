package spring.board.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.accounts.AccountRepository;
import spring.board.demo.domain.accounts.dto.AccountDetailResponse;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.exception.AccountNotFoundException;

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
