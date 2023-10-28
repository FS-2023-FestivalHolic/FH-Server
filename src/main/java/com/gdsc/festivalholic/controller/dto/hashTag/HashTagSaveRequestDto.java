package com.gdsc.festivalholic.controller.dto.hashTag;


import com.gdsc.festivalholic.domain.hashTag.HashTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HashTagSaveRequestDto {

    private String tagName;

    public HashTag toEntity() {

        return HashTag.builder()
                .tagName(tagName)
                .build();
    }
}
