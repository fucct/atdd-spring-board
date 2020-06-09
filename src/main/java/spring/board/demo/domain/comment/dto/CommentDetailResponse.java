package spring.board.demo.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String content;
}
