package spring.board.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleDetailResponse;
import spring.board.demo.dto.ArticleResponse;
import spring.board.demo.dto.ArticleUpdateRequest;
import spring.board.demo.dto.CommentRequest;
import spring.board.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Long> create(
        @Validated @RequestBody ArticleCreateRequest request) {
        Long id = articleService.save(request);

        return ResponseEntity.created(URI.create("/articles/" + id)).body(id);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticles() {
        List<ArticleResponse> articles = articleService.getArticles();

        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        ArticleResponse article = articleService.getArticleById(id);

        return ResponseEntity.ok(article);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ArticleDetailResponse> getDetailArticle(@PathVariable Long id) {
        ArticleDetailResponse article = articleService.getDetailArticleById(id);

        return ResponseEntity.ok(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
        @Validated @RequestBody ArticleUpdateRequest request) {
        articleService.updateById(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        articleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Long> addComment(@PathVariable Long id, @Validated @RequestBody CommentRequest commentRequest) {
        Long commentId = articleService.addComment(id, commentRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }
}
