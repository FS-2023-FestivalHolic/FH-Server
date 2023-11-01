package com.gdsc.festivalholic.controller.dto.beer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@NoArgsConstructor
public class BeerResponseDto {

    private Long beerId;
    private String beerName;
    private String introduction;
    private String content;
    private List<String> hashTagNames;
    private String imageUrl;

    @Builder
    public BeerResponseDto(Long beerId, String beerName, String introduction, String content, List<String> hashTagNames, String imageUrl){
        this.beerId = beerId;
        this.beerName = beerName;
        this.introduction = introduction;
        this.content = content;
        this.hashTagNames = hashTagNames;
        this.imageUrl = imageUrl;

    }

}
