package com.gdsc.festivalholic.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_EMPTY_LOGIN_ID(400, "로그인아이디 입력하세요.", 417),
    USER_EMPTY_PASSWORD(400, "비밀번호 입력하세요.", 418),
    USER_EMPTY_EMAIL(400, "이메일 입력하세요.", 419),
    USER_EMPTY_NAME(400, "이름 입력하세요.", 420),
    USER_EMPTY_NICKNAME(400, "닉네임 입력하세요.", 421);



    private final int status;
    private final String message;
    private final int code;
}
