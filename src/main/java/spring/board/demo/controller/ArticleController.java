package spring.board.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.article.dto.ArticleCreateRequest;
import spring.board.demo.domain.article.dto.ArticleCreateResponse;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.user.User;
import spring.board.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    //

    @AuthorizeCheck(check = true)
    @PostMapping
    public ResponseEntity<ArticleCreateResponse> create(@LoginUser User user,
        @Validated @RequestBody ArticleCreateRequest request) {
        ArticleCreateResponse response = articleService.save(user, request);

        return ResponseEntity.created(URI.create("/board/" + response.getId())).body(response);
    }

    @AuthorizeCheck(check = true)
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticle(@LoginUser User user) {
        List<ArticleResponse> responses = articleService.getArticles(user);
        return ResponseEntity.ok(responses);
    }
}
