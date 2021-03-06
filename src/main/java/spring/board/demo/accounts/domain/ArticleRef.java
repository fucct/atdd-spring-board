package spring.board.demo.accounts.domain;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Table("ACCOUNT_ARTICLE")
public class ArticleRef {

    private final Long article;
}
