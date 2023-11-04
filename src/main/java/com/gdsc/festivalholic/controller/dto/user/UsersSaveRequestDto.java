package com.gdsc.festivalholic.controller.dto.user;

import com.gdsc.festivalholic.domain.users.Users;
import lombok.*;

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
