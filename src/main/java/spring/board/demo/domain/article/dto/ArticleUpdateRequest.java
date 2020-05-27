package spring.board.demo.domain.article.dto;

public class ArticleUpdateRequest {

    private String title;
    private String content;

    public ArticleUpdateRequest() {
    }

    public ArticleUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
