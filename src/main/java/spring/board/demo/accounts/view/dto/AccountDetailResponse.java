package spring.board.demo.accounts.view.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.demo.articles.view.dto.ArticlePreviewResponse;
import spring.board.demo.comments.view.dto.CommentDetailResponse;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailResponse {

    private Long id;
    private String email;
    private String name;
    private List<ArticlePreviewResponse> articles;
    private List<CommentDetailResponse> comments;

    public static AccountDetailResponse of(AccountSampleDto sampleDto,
        List<ArticlePreviewResponse> articles, List<CommentDetailResponse> comments) {
        return new AccountDetailResponse(sampleDto.getId(), sampleDto.getEmail(),
            sampleDto.getName(), articles, comments);
    }
}
