package spring.board.demo.dto;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import spring.board.demo.domain.Comment;

@Getter @Setter
public class CommentResponseDto {

    private String member;
    private String content;
    private String createdDate;

    public CommentResponseDto(Comment comment) {
        this.member = comment.getNickName();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
