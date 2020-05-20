package spring.board.demo.dto;

import spring.board.demo.domain.Comment;

public class CommentResponse {

    private Long id;

    private String userName;

    private String content;

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getUserName(), comment.getContent());
    }

    public CommentResponse() {
    }

    public CommentResponse(Long id, String userName, String content) {
        this.id = id;
        this.userName = userName;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
