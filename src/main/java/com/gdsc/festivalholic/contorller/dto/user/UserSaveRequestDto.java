package com.gdsc.festivalholic.contorller.dto.user;

import com.gdsc.festivalholic.domain.user.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String loginId;
    private String password;
    private String email;
    private String name;
    private String nickName;

    @Builder
    public UserSaveRequestDto(String loginId, String password, String email, String name, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
