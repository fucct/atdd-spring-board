package spring.board.demo.domain.users.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.domain.article.dto.ArticleResponse;
import spring.board.demo.domain.comment.dto.CommentResponse;
import spring.board.demo.domain.users.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    private Long id;
    private String userId;
    private String userName;
    private List<ArticleResponse> articles;
    private List<CommentResponse> comments;

    public static UserDetailResponse of(User user, List<ArticleResponse> articles,
        List<CommentResponse> comments) {
        return new UserDetailResponse(user.getId(), user.getUserId(), user.getName(), articles,
            comments);
    }
}
