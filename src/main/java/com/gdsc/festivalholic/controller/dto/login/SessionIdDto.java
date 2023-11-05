package com.gdsc.festivalholic.controller.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessionIdDto {

    private String sessionId;

    @Builder
    public SessionIdDto(String sessionId) {
        this.sessionId = sessionId;
    }

}
