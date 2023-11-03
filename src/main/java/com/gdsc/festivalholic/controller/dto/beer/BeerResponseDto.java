package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.controller.dto.beerContent.BeerContentDto;
import com.gdsc.festivalholic.domain.beerContent.BeerContent;
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
    private Integer likesCnt;
    private List<BeerContentDto> beerContentList;
    private List<String> hashTagNames;
    private String imageUrl;

    @Builder
    public BeerResponseDto(Long beerId, String beerName, String introduction, Integer likesCnt, List<BeerContentDto> beerContentList, List<String> hashTagNames, String imageUrl){
        this.beerId = beerId;
        this.beerName = beerName;
        this.introduction = introduction;
        this.likesCnt = likesCnt;
        this.beerContentList = beerContentList;
        this.hashTagNames = hashTagNames;
        this.imageUrl = imageUrl;

    }

}
