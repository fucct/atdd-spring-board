package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleResponse;
import spring.board.demo.dto.ArticleUpdateRequest;
import spring.board.demo.exception.IdNotFoundException;
import spring.board.demo.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long save(ArticleCreateRequest request) {
        Article persistArticle = articleRepository.save(request.toArticle());

        return persistArticle.getId();
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll().stream()
            .map(ArticleResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));

        return ArticleResponse.of(article);
    }

    public void updateById(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));
        article.update(request);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
