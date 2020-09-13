package spring.board.demo.comments;

import java.util.List;

import org.springframework.data.jdbc.core.convert.EntityRowMapper;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import lombok.RequiredArgsConstructor;
import spring.board.demo.comments.view.dto.CommentDetailResponse;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final NamedParameterJdbcOperations operations;
    private final EntityRowMapper<CommentDetailResponse> rowMapper;

    @SuppressWarnings("unchecked")
    public CommentCustomRepositoryImpl(NamedParameterJdbcOperations operations,
        RelationalMappingContext mappingContext, JdbcConverter jdbcConverter) {

        this.operations = operations;
        this.rowMapper = new EntityRowMapper<>(
            (RelationalPersistentEntity<CommentDetailResponse>)mappingContext.getRequiredPersistentEntity(
                CommentDetailResponse.class), jdbcConverter);
    }

    @Override
    public List<CommentDetailResponse> findCommentsByIds(List<Long> commentIds) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("commentIds", commentIds);

        final List<CommentDetailResponse> comments = this.operations.query(this.findCommentsByIdsSql(),
            parameterSource, rowMapper);
        return comments;
    }

    private String findCommentsByIdsSql() {
        return null;
    }
}
