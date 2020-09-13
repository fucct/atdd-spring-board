package spring.board.demo.accounts.view.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSampleDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private List<Long> articleIds;
    private List<Long> commentIds;
}
