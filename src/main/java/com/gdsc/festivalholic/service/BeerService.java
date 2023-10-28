package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beer.BeerRepository;
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

    public Long save(BeerSaveRequestDto beerSaveRequestDto) {
        Beer beer = beerSaveRequestDto.toEntity();
        Long beerId = beerRepository.save(beer).getId();
        return beerId;
    }

}
