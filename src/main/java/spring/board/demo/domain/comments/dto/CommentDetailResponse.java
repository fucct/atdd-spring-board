package spring.board.demo.domain.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetailResponse {
    private Long id;
    private Long accountId;
    private String accountName;
    private String content;
}
