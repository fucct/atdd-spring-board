package spring.board.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleResponseDto;
import spring.board.demo.repository.ArticleRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleResponseDto> findArticles(){
        return articleRepository.findAll();
    }

    @Transactional
    public Long save(Article article) {
        return articleRepository.save(article);
    }

    public ArticleResponseDto findById(Long articleId) {
        return ArticleResponseDto.of(articleRepository.findById(articleId));
    }
}
