package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerListResponseDto;
import com.gdsc.festivalholic.controller.dto.hashTag.HashTagResponseDto;
import com.gdsc.festivalholic.controller.dto.hashTag.HashTagSaveRequestDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.hashTag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public Long create(HashTagSaveRequestDto hashTagSaveRequestDto){

        HashTag hashTag = hashTagSaveRequestDto.toEntity();

        Long hashTagId = hashTagRepository.save(hashTag).getId();

        return hashTagId;
    }


}
