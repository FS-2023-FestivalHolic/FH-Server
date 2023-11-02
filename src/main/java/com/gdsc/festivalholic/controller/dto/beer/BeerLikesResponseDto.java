package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.domain.beerImage.BeerImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URL;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "좋아요한 맥주 응답")
@Getter
@NoArgsConstructor
public class BeerLikesResponseDto {

    @Schema(description = "맥주 이름")
    private String beerName;

    @Schema(description = "맥주 이미지")
    private URL beerImage;

    @Schema(description = "좋아요 수")
    private Integer likesCnt;

    @Builder
    public BeerLikesResponseDto(String beerName, URL beerImage, Integer likesCnt) {
        this.beerName = beerName;
        this.beerImage = beerImage;
        this.likesCnt = likesCnt;
    }

}
