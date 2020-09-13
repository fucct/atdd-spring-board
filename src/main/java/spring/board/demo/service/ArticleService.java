package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.Article;
import spring.board.demo.domain.articles.ArticleRepository;
import spring.board.demo.domain.articles.CommentRef;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.domain.comments.CommentRepository;
import spring.board.demo.domain.comments.dto.CommentDetailResponse;
import spring.board.demo.exception.ArticleNotFoundException;

@Service
@Transactional
public class ArticleService {
    private final AccountService accountService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleService(AccountService accountService,
        ArticleRepository articleRepository,
        CommentRepository commentRepository) {
        this.accountService = accountService;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticlePreviewResponse> getArticles() {
        return articleRepository.findAllWithAccountName();
    }

    public void delete(Long id, Account account) {
        articleRepository.deleteById(id);
        accountService.save(account);
    }
}
