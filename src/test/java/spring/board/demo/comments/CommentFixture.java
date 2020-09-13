package spring.board.demo.comments;

import spring.board.demo.accounts.AccountFixture;
import spring.board.demo.articles.domain.Article;

public class CommentFixture {
    public static final Long ID1 = 1L;
    public static final Long ID2 = 2L;
    public static final String CONTENT1 = "안녕하세요 우아한 테크코스 2기 김태헌입니다.";
    public static final String CONTENT2 = "반갑습니다. 우아한 테크코스 2기 디디입니다.";

    public static Comment createWithoutId() {
        return Comment.builder()
            .accountId(AccountFixture.ID1)
            .content(CONTENT1)
            .build();
    }

    public static Comment createWithId(Long id) {
        return Comment.builder()
            .id(id)
            .accountId(AccountFixture.ID1)
            .content(CONTENT1)
            .build();
    }
}
