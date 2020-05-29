package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.article.Article;
import spring.board.demo.domain.article.ArticleRepository;
import spring.board.demo.domain.article.dto.ArticleCreateRequest;
import spring.board.demo.domain.article.dto.ArticleCreateResponse;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.user.User;

@Service
@Slf4j
@Transactional
public class ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    public ArticleService(UserService userService,
        ArticleRepository articleRepository, CommentService commentService) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    public ArticleCreateResponse save(User user, ArticleCreateRequest request) {
        Article article = request.toArticle(user);
        Article persistArticle = articleRepository.save(article);
        user.addArticle(article);
        userService.save(user);
        return ArticleCreateResponse.of(persistArticle);
    }

    public List<ArticleResponse> getArticles() {
        List<Article> articles = articleRepository.findAll();
        List<String> users = articles.stream()
            .map(article -> article.getUserRef().getUserName())
            .collect(Collectors.toList());
        return ArticleResponse.listOf(articles, users);
    }
}
