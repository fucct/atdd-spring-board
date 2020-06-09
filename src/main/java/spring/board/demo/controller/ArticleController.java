package spring.board.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.controller.prehandler.AuthorizeCheck;
import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.accounts.Account;
import spring.board.demo.domain.articles.dto.ArticleCreateResponse;
import spring.board.demo.domain.articles.dto.ArticleDetailResponse;
import spring.board.demo.domain.articles.dto.ArticlePreviewResponse;
import spring.board.demo.domain.articles.dto.ArticleRequest;
import spring.board.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @AuthorizeCheck
    @PostMapping
    public ResponseEntity<ArticleCreateResponse> create(@LoginUser Account account,
        @Valid @RequestBody ArticleRequest request) {
        ArticleCreateResponse response = articleService.save(account, request);

        return ResponseEntity
            .created(URI.create("/board/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticlePreviewResponse>> getArticles() {
        List<ArticlePreviewResponse> responses = articleService.getArticles();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailResponse> getArticle(@PathVariable("id") Long id) {
        ArticleDetailResponse response = articleService.getArticle(id);
        return ResponseEntity.ok(response);
    }

    @AuthorizeCheck
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable("id") Long id,
        @LoginUser Account account,
        @Valid @RequestBody ArticleRequest request) {
        articleService.update(id, account, request);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id,
        @LoginUser Account account) {
        articleService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
