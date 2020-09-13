package spring.board.demo.articles.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.accounts.service.AccountService;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.ArticleRepository;
import spring.board.demo.articles.view.dto.ArticlePreviewResponse;
import spring.board.demo.comments.CommentRepository;

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
