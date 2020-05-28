package spring.board.demo.domain.article;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    List<Article> findAll();

    @Override
    List<Article> findAllById(Iterable<Long> ids);
}
