package com.gdsc.festivalholic.controller.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String loginId;
    private String password;

}
