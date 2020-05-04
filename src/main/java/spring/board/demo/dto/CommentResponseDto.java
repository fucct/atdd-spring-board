package spring.board.demo.dto;

import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import spring.board.demo.domain.Comment;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String commentNickname;
    private String commentContent;
    private String createdDate;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(
            comment.getId(),
            comment.getNickName(),
            comment.getContent(),
            comment.getCreatedDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
