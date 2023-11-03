package com.gdsc.festivalholic.controller.dto.beerContent;

import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beerContent.BeerContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeerContentDto {

    private String subject;
    private String description;

    @Builder
    public BeerContentDto(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public BeerContent toEntity() {
        return BeerContent.builder()
                .subject(subject)
                .description(description)
                .build();
    }
}
