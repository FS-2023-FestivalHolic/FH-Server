package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.domain.beer.Beer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class BeerSaveRequestDto {

    private String beerName;
    private String introduction;
    private String content;
    private List<Long> hashTagIds;

    @Builder
    public BeerSaveRequestDto(String beerName, String introduction, String content, List<Long> hashTagIds) {
        this.beerName = beerName;
        this.introduction = introduction;
        this.content = content;
        this.hashTagIds = hashTagIds;
    }

    public Beer toEntity() {
        return Beer.builder()
                .beerName(beerName)
                .introduction(introduction)
                .content(content)
                .build();
    }
}
