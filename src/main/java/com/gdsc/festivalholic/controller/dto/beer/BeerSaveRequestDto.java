package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.domain.beer.Beer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BeerSaveRequestDto {

    private String beer_name;
    private String introduction;
    private String content;
    private List<Long> hashTagIds;

    @Builder
    public BeerSaveRequestDto(String beer_name, String introduction, String content, List<Long> hashTagIds) {
        this.beer_name = beer_name;
        this.introduction = introduction;
        this.content = content;
        this.hashTagIds = hashTagIds;
    }

    public Beer toEntity() {
        return Beer.builder()
                .beerName(beer_name)
                .introduction(introduction)
                .content(content)
                .build();
    }
}
