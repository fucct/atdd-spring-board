package spring.board.demo.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import lombok.extern.slf4j.Slf4j;
import spring.board.demo.domain.comment.Comment;
import spring.board.demo.domain.comment.CommentRepository;
import spring.board.demo.domain.comment.dto.CommentDetailResponse;
import spring.board.demo.domain.users.User;
import spring.board.demo.domain.users.UserRepository;

@DataJdbcTest
@Slf4j
public class CommentRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void create() {
        User user1 = User.of("abcd", "디디", "1234");
        User user2 = User.of("1234", "노루", "abcd");
        userRepository.saveAll(Arrays.asList(user1, user2));
        assertThat(userRepository.findAll()).hasSize(3);
        Comment comment1 = Comment.builder()
            .userId(2L)
            .content("안녕")
            .build();
        Comment comment2 = Comment.builder()
            .userId(3L)
            .content("메롱")
            .build();
        Iterable<Comment> comments = commentRepository.saveAll(Arrays.asList(comment1, comment2));
        assertThat(comments).hasSize(2);
        List<CommentDetailResponse> commentResponses = commentRepository.findCommentsByIds(
            Arrays.asList(1L, 2L));
        assertThat(commentResponses).hasSize(2).extracting(CommentDetailResponse::getUserName)
            .containsExactlyInAnyOrder("디디", "노루");

    }
}
