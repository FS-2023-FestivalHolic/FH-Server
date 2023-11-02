package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.hashTag.HashTagResponseDto;
import com.gdsc.festivalholic.controller.dto.hashTag.HashTagSaveRequestDto;
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

    public Long create(HashTagSaveRequestDto hashTagSaveRequestDto) {

        HashTag hashTag = hashTagSaveRequestDto.toEntity();

        Long hashTagId = hashTagRepository.save(hashTag).getId();

        return hashTagId;
    }

    public List<HashTagResponseDto> findAllHashTag() {
        List<HashTag> all = hashTagRepository.findAll();
        List<HashTagResponseDto> hashTagResponseDtoList = new ArrayList();
        for (int i = 0; i < all.size(); i++) {
            HashTag hashTag = all.get(i);

            HashTagResponseDto hashTagResponseDto = HashTagResponseDto.builder()
                    .hashTagName(hashTag.getTagName())
                    .id(hashTag.getId())
                    .build();

            hashTagResponseDtoList.add(hashTagResponseDto);

        }

        return hashTagResponseDtoList;

    }
}

