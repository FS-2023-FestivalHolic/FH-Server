package com.gdsc.festivalholic.controller.dto.beer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BeerListResponseDto {

    private String beerName;
    private String imageUrl;
    private List<String> hashTagList;
    private Integer likeNum;

    @Builder
    public BeerListResponseDto(String beerName, String imageUrl, List<String> hashTagList, Integer likeNum) {
        this.beerName = beerName;
        this.imageUrl = imageUrl;
        this.hashTagList = hashTagList;
        this.likeNum = likeNum;
    }
}
