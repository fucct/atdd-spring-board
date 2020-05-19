package spring.board.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import spring.board.demo.domain.Article;
import spring.board.demo.domain.Comment;
import spring.board.demo.dto.ArticleCreateRequest;
import spring.board.demo.dto.ArticleDetailResponse;
import spring.board.demo.dto.ArticleResponse;
import spring.board.demo.dto.ArticleUpdateRequest;
import spring.board.demo.dto.CommentRequest;
import spring.board.demo.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentService commentService;

    private ArticleService articleService;

    Article article1;
    Article article2;
    Article article3;

    Comment comment1;
    Comment comment2;
    Comment comment3;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository, commentService);

        article1 = Article.of(1L, "안녕하세요", "디디", "우아한 테크코스 2기 디디 김태헌입니다.");
        article2 = Article.of(2L, "반갑습니다", "카일", "우아한 테크코스 2기 카일 김시영입니다.");

        comment1 = Comment.of(1L, "카일", "반갑습니다");
        comment2 = Comment.of(2L, "호돌", "잘부탁드립니다");
        comment3 = Comment.of(3L, "디디", "네 안녕하세요");

        article3 = new Article(3L, "여어", "호돌", "히사시부리",
            Sets.newLinkedHashSet(comment1, comment2, comment3));

    }

    @Test
    @DisplayName("게시글을 저장한다")
    void createArticle() {
        when(articleRepository.save(any(Article.class))).thenReturn(article1);
        ArticleCreateRequest request1 = new ArticleCreateRequest("안녕하세요", "디디",
            "우아한 테크코스 2기 디디 김태헌입니다.");

        assertThat(articleService.save(request1)).isEqualTo(1L);
    }

    @Test
    @DisplayName("댓글을 포함하지 않은 게시글들을 가져온다")
    void getArticles() {
        List<Article> articles = Arrays.asList(article1, article2, article3);
        List<ArticleResponse> responses = ArticleResponse.listOf(
            articles);

        when(articleRepository.findAll()).thenReturn(articles);

        assertThat(articleService.getArticles())
            .usingFieldByFieldElementComparator()
            .containsExactlyInAnyOrderElementsOf(responses);
    }

    @Test
    @DisplayName("아이디에 해당하는 게시글을 가져온다")
    void getArticle() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article1));
        assertThat(articleService.getArticleById(article1.getId())).isEqualToComparingFieldByField(
            ArticleResponse.of(article1));
    }

    @Test
    @DisplayName("게시글을 수정한다")
    void updateArticle() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article1));
        articleService.updateById(article1.getId(), new ArticleUpdateRequest("바보", "ㅋㅋ"));
        assertThat(article1.getTitle()).isEqualTo("바보");
        assertThat(article1.getContent()).isEqualTo("ㅋㅋ");
    }

    @Test
    @DisplayName("댓글을 포함한 게시글을 가져온다.")
    void getDetailArticle() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article3));
        when(commentService.findAllById(anyList())).thenReturn(
            Arrays.asList(comment1, comment2, comment3));

        ArticleDetailResponse article = articleService.getDetailArticleById(article3.getId());

        assertThat(article.getTitle()).isEqualTo("여어");
        assertThat(article.getUserName()).isEqualTo("호돌");
        assertThat(article.getContent()).isEqualTo("히사시부리");
        assertThat(article.getComments()).hasSize(3).containsExactly(comment1, comment2, comment3);
    }

    @Test
    @DisplayName("게시글에 댓글을 추가한다.")
    void addComment() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article1));

        articleService.addComment(article1.getId(), new CommentRequest("카일", "반갑습니다"));
        articleService.addComment(article1.getId(), new CommentRequest("호돌", "잘부탁드립니다"));
        articleService.addComment(article1.getId(), new CommentRequest("디디", "네 안녕하세요"));

        assertThat(article1.getComments()).hasSize(3)
            .extracting(Comment::getUserName)
            .containsExactly("카일", "호돌", "디디");

        assertThat(article1.getComments()).extracting(Comment::getContent)
            .containsExactly("반갑습니다", "잘부탁드립니다", "네 안녕하세요");
    }
}