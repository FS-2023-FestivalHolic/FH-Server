package com.gdsc.festivalholic.controller.dto.beer;

import com.gdsc.festivalholic.controller.dto.beerContent.BeerContentDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beerContent.BeerContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BeerSaveRequestDto {

    private String beerName;
    private String introduction;
    private List<BeerContentDto> beerContentDtoList;
    private List<Long> hashTagIds;
    private Integer likesCnt;

    @Builder
    public BeerSaveRequestDto(String beerName, String introduction, List<BeerContentDto> beerContentDtoList, List<Long> hashTagIds, Integer likesCnt) {
        this.beerName = beerName;
        this.introduction = introduction;
        this.beerContentDtoList = beerContentDtoList;
        this.hashTagIds = hashTagIds;
        this.likesCnt = likesCnt;
    }

    public Beer toEntity() {
        return Beer.builder()
                .beerName(beerName)
                .introduction(introduction)
                .likesCnt(likesCnt)
                .beerContentList(toBeerContentListEntity(beerContentDtoList))
                .build();
    }

    public List<BeerContent> toBeerContentListEntity(List<BeerContentDto> beerContentList){
        List<BeerContent> beerContents = new ArrayList<>();
        for(BeerContentDto beerContentDto : beerContentList){
            BeerContent beerContent = beerContentDto.toEntity();
            beerContents.add(beerContent);
        }

        return beerContents;
    }
}
