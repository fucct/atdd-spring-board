package spring.board.demo.articles;

import spring.board.demo.accounts.domain.Account;
import spring.board.demo.articles.domain.Article;

public class ArticleFixture {
    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final String TITLE1 = "안녕하세요";
    public static final String TITLE2 = "반갑습니다";
    public static final String CONTENT1 = "안녕하세요 우아한 테크코스 2기 김태헌입니다.";
    public static final String CONTENT2 = "반갑습니다. 우아한 테크코스 2기 디디입니다.";

    public static Article createWithoutId() {
        return Article.builder()
            .title(TITLE1)
            .content(CONTENT1)
            .build();
    }

    public static Article createWithId(Long id) {
        return Article.builder()
            .id(id)
            .title(TITLE1)
            .content(CONTENT1)
            .build();
    }

}
