package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleResponse;
import spring.board.demo.exception.IllegalIdException;
import spring.board.demo.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleResponse save(ArticleCreateRequest request) {
        Article persistArticle = articleRepository.save(request.toArticle());

        return ArticleResponse.of(persistArticle);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticles() {
        return articleRepository.findAll().stream()
            .map(ArticleResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(IllegalIdException::new);

        return ArticleResponse.of(article);
    }

    public void updateById(Long id, ArticleCreateRequest request) {
        Article article = articleRepository.findById(id).orElseThrow(IllegalIdException::new);
        article.update(request);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
