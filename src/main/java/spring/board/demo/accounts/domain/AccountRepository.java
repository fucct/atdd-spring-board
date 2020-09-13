package spring.board.demo.accounts.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import spring.board.demo.accounts.view.dto.AccountSampleDto;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("select * from account where email = :email")
    Optional<Account> findByEmail(@Param("email") String email);

    @Override
    List<Account> findAll();

    @Query("SELECT account.id AS id, account.email AS email, account.name AS name, "
        + "account.password AS password, article.id AS article_ids, comments.id AS comment_ids "
        + "FROM account "
        + "LEFT JOIN article ON account.id = article.account_id "
        + "LEFT JOIN comments ON account.id = comments.account_id "
        + "WHERE account.id = :id")
    Optional<AccountSampleDto> findSampleById(@Param("id") Long id);
}
