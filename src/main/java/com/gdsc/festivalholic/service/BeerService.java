package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beer.BeerRepository;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTagRepository;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.hashTag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class BeerService {

    private final BeerRepository beerRepository;
    private final HashTagRepository hashTagRepository;
    private final BeerHashTagRepository beerHashTagRepository;

    public Long create(BeerSaveRequestDto beerSaveRequestDto) {
        Beer beer = beerSaveRequestDto.toEntity();

        for(Long hashTagId : beerSaveRequestDto.getHashTagIds()){
            HashTag hashTag = hashTagRepository.findById(hashTagId)
                    .orElseThrow(() -> new IllegalArgumentException("해시태그를 찾을 수 없습니다. ID = " + hashTagId));
            BeerHashTag beerHashTag = new BeerHashTag(beer, hashTag);
            beer.addBeerHashTag(beerHashTag);
            hashTag.addBeerHashTag(beerHashTag);

            beerHashTagRepository.save(beerHashTag);
        }

        Long beerId = beerRepository.save(beer).getId();
        return beerId;
    }

}
