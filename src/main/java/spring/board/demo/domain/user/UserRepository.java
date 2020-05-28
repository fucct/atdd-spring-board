package spring.board.demo.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select * from user where user_id = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);

    @Override
    List<User> findAll();

    @Query("select * from user where article_user.article in (:ids)")
    List<User> findAllByArticleIds(@Param("ids") List<Long> ids);
}
