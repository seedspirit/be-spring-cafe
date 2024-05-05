package codesquad.springcafe.model.user.dto;

import codesquad.springcafe.model.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreationDto {
    private final String userId;
    private final String nickname;
    private final String password;
    private final String email;
    private final LocalDateTime registerTime;

    public UserCreationDto(String userId, String nickname, String password, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.registerTime = LocalDateTime.now();
    }

    public User toEntity() {
        return new User(userId, nickname, password, email, registerTime);
    }
}
