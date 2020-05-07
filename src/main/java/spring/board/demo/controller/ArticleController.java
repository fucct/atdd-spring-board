package spring.board.demo.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleResponse;

@RestController
public class ArticleController {

    @PostMapping("/articles")
    public ResponseEntity createPost(@RequestBody ArticleCreateRequest request) {

        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("/articles/list")
    public ResponseEntity getArticles() {
        List<ArticleResponse> articles = Arrays.asList(new ArticleResponse(),
            new ArticleResponse());

        return ResponseEntity.ok(articles);
    }
}
