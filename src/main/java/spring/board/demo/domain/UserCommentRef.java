package spring.board.demo.domain;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Getter
@Table("user_comment")
public class UserCommentRef {
    private Long comment;


}
