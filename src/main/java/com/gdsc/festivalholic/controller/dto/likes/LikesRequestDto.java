package com.gdsc.festivalholic.controller.dto.likes;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "좋아요 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRequestDto {

    @Schema(description = "유저 인덱스")
    private Long userId;

    @Schema(description = "맥주 인덱스")
    private Long beerId;

    public LikesRequestDto(Long userId, Long beerId) {
        this.beerId = beerId;
        this.userId = userId;
    }

}
