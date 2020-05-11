package spring.board.demo.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleResponse;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static Map<Long, Article> articleRepository = new HashMap<>();
    private static Long currentId = 1L;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ArticleCreateRequest request) {
        articleRepository.put(currentId, request.toArticle());
        return ResponseEntity.created(URI.create("/articles/" + currentId++)).build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticles() {
        List<ArticleResponse> articles = articleRepository.values()
            .stream()
            .map(ArticleResponse::of)
            .collect(Collectors.toList());

        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        ArticleResponse article = ArticleResponse.of(articleRepository.get(id));

        return ResponseEntity.ok(article);
    }
}
