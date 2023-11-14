package com.gdsc.festivalholic.controller.dto.beer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "해당 맥주에 대한 유저 좋아요 상태")
@Getter
@NoArgsConstructor
public class BeerLikeStatusResponse {

    private boolean likeStatus = false;

    public void like(){
        likeStatus = true;
    }

    @Builder
    public BeerLikeStatusResponse(boolean likeStatus) {
        this.likeStatus = likeStatus;
    }
}
