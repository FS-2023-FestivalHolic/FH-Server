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
    private String nickName;

    @Builder
    public UsersSaveRequestDto(String loginId, String password, String email, String name, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }

    public Users toEntity() {
        return Users.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
