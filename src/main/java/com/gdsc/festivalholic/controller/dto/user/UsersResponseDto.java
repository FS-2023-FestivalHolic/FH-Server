package com.gdsc.festivalholic.controller.dto.user;

import com.gdsc.festivalholic.controller.dto.beer.BeerLikesResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "마이 페이지 응답")
@Getter
@NoArgsConstructor
public class UsersResponseDto {

    @Schema(description = "유저 이름")
    private String userName;

    @Schema(description = "좋아요한 맥주 리스트")
    private List<BeerLikesResponseDto> beerList;

    @Builder
    public UsersResponseDto(String userName, List<BeerLikesResponseDto> beerList) {
        this.userName = userName;
        this.beerList = beerList;
    }

}
