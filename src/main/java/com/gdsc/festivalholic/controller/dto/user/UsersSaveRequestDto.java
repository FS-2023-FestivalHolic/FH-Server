package com.gdsc.festivalholic.controller.dto.user;

import static com.gdsc.festivalholic.config.exception.ErrorCode.USER_EMPTY_LOGIN_ID;

import com.gdsc.festivalholic.domain.users.Users;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {

    private String loginId;
    private String password;
    private String email;
    private String name;
    private String nickname;

    public Users toEntity() {
        return Users.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build();
    }
}
