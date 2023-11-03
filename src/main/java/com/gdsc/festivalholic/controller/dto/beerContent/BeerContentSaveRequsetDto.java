package com.gdsc.festivalholic.controller.dto.beerContent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeerContentSaveRequsetDto {

    private String subject;
    private String description;

    @Builder
    public BeerContentSaveRequsetDto(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }
}
