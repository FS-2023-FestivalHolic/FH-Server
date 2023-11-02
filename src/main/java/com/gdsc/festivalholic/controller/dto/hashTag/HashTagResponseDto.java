package com.gdsc.festivalholic.controller.dto.hashTag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashTagResponseDto {

    private Long id;
    private String hashTagName;

    @Builder
    public HashTagResponseDto(Long id, String hashTagName) {
        this.id = id;
        this.hashTagName = hashTagName;
    }

}
