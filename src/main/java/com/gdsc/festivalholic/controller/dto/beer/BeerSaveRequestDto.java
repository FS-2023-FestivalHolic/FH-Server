package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.domain.beer.Beer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeerSaveRequestDto {

    private String beer_name;
    private String introduction;
    private String content;

    @Builder
    public BeerSaveRequestDto(String beer_name, String introduction, String content) {
        this.beer_name = beer_name;
        this.introduction = introduction;
        this.content = content;
    }

    public Beer toEntity() {
        return Beer.builder()
                .beer_name(beer_name)
                .introduction(introduction)
                .content(content)
                .build();
    }
}
