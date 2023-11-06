package com.gdsc.festivalholic.controller;


import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.beer.BeerListResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.controller.dto.beerImage.BeerImageUploadDto;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.service.BeerService;
import com.gdsc.festivalholic.service.HashTagService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/beers")
public class BeerController {


    private final BeerService beerService;
    private final HashTagService hashtagService;


    //수제 맥주 생성 API
    @PostMapping("/create")
    public ResponseDto<Long> create(@RequestPart(value = "beerImageUploadDto") MultipartFile multipartFile,
                                    @RequestPart(value = "beerSaveRequestDto") BeerSaveRequestDto beerSaveRequestDto){
        BeerImageUploadDto beerImageUploadDto = new BeerImageUploadDto(multipartFile);
        Long beerId = beerService.create(beerSaveRequestDto, beerImageUploadDto);

        return ResponseUtil.SUCCESS("맥주 정보 생성을 완료하였습니다.", beerId);
    }

    //특정 수제 맥주 조회 API
    @GetMapping("/{id}")
    public ResponseDto<BeerResponseDto> findBeerById(@PathVariable("id") Long beerId){

        BeerResponseDto beerById = beerService.findBeerById(beerId);

        return ResponseUtil.SUCCESS("맥주 정보 조회를 완료하였습니다.", beerById);

    }

    @GetMapping("")
    public ResponseDto<List<BeerListResponseDto>> findAllBeer(){

        List<BeerListResponseDto> allBeer = beerService.findAllBeer();

        return ResponseUtil.SUCCESS("모든 맥주 정보 조회를 완료하였습니다.", allBeer);
    }

    @GetMapping("/likes")
    public ResponseDto<List<BeerListResponseDto>> findAllBeerByOrderByLikesCnt(){
        List<BeerListResponseDto> allBeerByOrderByLikesCnt = beerService.findAllBeerByOrderByLikesCnt();

        return ResponseUtil.SUCCESS("좋아요 순으로 모든 맥주 정보 조회를 완료하였습니다.", allBeerByOrderByLikesCnt);
    }

    @GetMapping("/name")
    public ResponseDto<List<BeerListResponseDto>> findAllBeerByOrderByBeerName(){
        List<BeerListResponseDto> allBeerByOrderByBeerName = beerService.findAllBeerByOrderByBeerName();

        return ResponseUtil.SUCCESS("좋아요 순으로 모든 맥주 정보 조회를 완료하였습니다.", allBeerByOrderByBeerName);
    }

    @GetMapping("/hashTags")
    public ResponseDto<List<BeerListResponseDto>> findBeersByHasTags(@RequestParam List<Long> hashTagIds){
        System.out.println(hashTagIds.get(0));
        List<BeerListResponseDto> beers = beerService.findBeersByHashTags(hashTagIds);
        return ResponseUtil.SUCCESS("입력받은 해쉬태그 기준으로 맥주 정보 조회를 완료하였습니다.", beers);
    }

}
