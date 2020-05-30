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

import spring.board.demo.controller.prehandler.LoginUser;
import spring.board.demo.domain.article.dto.ArticleRequest;
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

    @AuthorizeCheck(check = true)
    @PostMapping
    public ResponseEntity<ArticleResponse> create(@LoginUser User user,
        @Valid @RequestBody ArticleRequest request) {
        ArticleResponse response = articleService.save(user, request);

        return ResponseEntity
            .created(URI.create("/board/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticles() {
        List<ArticleResponse> responses = articleService.getArticles();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        ArticleResponse response = articleService.getArticle(id);
        return ResponseEntity.ok(response);
    }

    @AuthorizeCheck(check = true)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable("id") Long id, @LoginUser User user,
        @Valid @RequestBody ArticleRequest request) {
        articleService.update(id, user, request);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeCheck(check = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id, @LoginUser User user) {
        articleService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
