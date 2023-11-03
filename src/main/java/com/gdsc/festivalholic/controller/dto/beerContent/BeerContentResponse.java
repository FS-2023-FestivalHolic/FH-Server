package com.gdsc.festivalholic.controller.dto.beerContent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BeerContentResponse {

    private String subject;
    private String description;

    @Builder
    public BeerContentResponse(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }
}
