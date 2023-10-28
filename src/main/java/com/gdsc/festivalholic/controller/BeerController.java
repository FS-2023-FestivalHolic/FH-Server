package com.gdsc.festivalholic.controller;


import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private final BeerService beerService;

    //수제 맥주 생성 API
    @PostMapping("/create")
    public ResponseDto<Long> create(@RequestBody BeerSaveRequestDto beerSaveRequestDto){
        //예외처리 넣기


        Long beerId = beerService.save(beerSaveRequestDto);

        return ResponseUtil.SUCCESS("맥주 정보 생성을 완료하였습니다.", beerId);
    }


}
