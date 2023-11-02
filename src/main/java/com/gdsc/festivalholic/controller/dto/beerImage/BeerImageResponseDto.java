package com.gdsc.festivalholic.controller.dto.beerImage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "맥주 이미지 응답")
@Getter
@NoArgsConstructor
public class BeerImageResponseDto {

    @Schema(description = "맥주 이미지")
    private String url;

    @Builder
    public BeerImageResponseDto(String url) {
        this.url = url;
    }

}
