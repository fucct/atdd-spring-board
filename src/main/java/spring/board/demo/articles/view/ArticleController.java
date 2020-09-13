package spring.board.demo.articles.view;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.board.demo.common.prehandler.AuthorizeCheck;
import spring.board.demo.common.prehandler.LoginUser;
import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.view.dto.ArticleCreateResponse;
import spring.board.demo.articles.view.dto.ArticlePreviewResponse;
import spring.board.demo.articles.view.dto.ArticleRequest;
import spring.board.demo.articles.service.ArticleService;

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

        return null;
    }

    @GetMapping
    public ResponseEntity<List<ArticlePreviewResponse>> getArticles() {
        List<ArticlePreviewResponse> responses = articleService.getArticles();
        return ResponseEntity.ok(responses);
    }


    @AuthorizeCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id,
        @LoginUser Account account) {
        articleService.delete(id, account);
        return ResponseEntity.noContent().build();
    }
}
