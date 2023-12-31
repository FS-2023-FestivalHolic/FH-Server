package com.gdsc.festivalholic.controller;


import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.hashTag.HashTagResponseDto;
import com.gdsc.festivalholic.controller.dto.hashTag.HashTagSaveRequestDto;
import com.gdsc.festivalholic.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hashTags")
public class HashTagController {

    private final HashTagService hashTagService;

    @PostMapping("/create")
    public ResponseDto<Long> create(@RequestBody HashTagSaveRequestDto hashTagSaveRequestDto){
        //예외처리 넣기

        Long hashTagId = hashTagService.create(hashTagSaveRequestDto);

        return ResponseUtil.SUCCESS("해쉬태그 생성 완료하였습니다.", hashTagId);
    }

    @GetMapping("")
    public ResponseDto<List<HashTagResponseDto>> findAllHashTag(){

        List<HashTagResponseDto> allHashTag = hashTagService.findAllHashTag();

        return ResponseUtil.SUCCESS("모든 해쉬태그 조회를 완료하였습니다.", allHashTag);
    }
}
