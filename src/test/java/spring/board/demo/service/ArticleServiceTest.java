package spring.board.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    private ArticleService articleService;

    Article article1;
    Article article2;
    Article article3;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository);

        article1 = Article.of("안녕하세요", "디디", "우아한 테크코스 2기 디디 김태헌입니다.");
        article2 = Article.of("반갑습니다", "카일", "우아한 테크코스 2기 카일 김시영입니다.");
        article3 = Article.of("여어", "호돌", "히사시부리");

    }

    @Test
    void createArticle() {
        ArticleCreateRequest request1 = new ArticleCreateRequest("안녕하세요", "디디",
            "우아한 테크코스 2기 디디 김태헌입니다.");
        ArticleCreateRequest request2 = new ArticleCreateRequest("반갑습니다", "카일",
            "우아한 테크코스 2기 카일 김시영입니다.");
        ArticleCreateRequest request3 = new ArticleCreateRequest("여어", "호돌", "히사시부리");

    }
}