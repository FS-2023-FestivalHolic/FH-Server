package com.gdsc.festivalholic.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gdsc.festivalholic.controller.dto.beer.BeerListResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.controller.dto.beerImage.BeerImageUploadDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beer.BeerRepository;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTagRepository;
import com.gdsc.festivalholic.domain.beerImage.BeerImage;
import com.gdsc.festivalholic.domain.beerImage.BeerImageRepository;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.hashTag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class BeerService {

    private final BeerRepository beerRepository;
    private final HashTagRepository hashTagRepository;
    private final BeerHashTagRepository beerHashTagRepository;
    private final BeerImageRepository beerImageRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Long create(BeerSaveRequestDto beerSaveRequestDto, BeerImageUploadDto beerImageUploadDto) {
        Beer beer = beerSaveRequestDto.toEntity();
        MultipartFile file = beerImageUploadDto.getFile();

        addHashTagsToBeer(beerSaveRequestDto, beer);
        Long beerId = beerRepository.save(beer).getId();

        // 이미지 부분
        if (file != null && !file.isEmpty()) {
            uploadImage(file, beer, beerId);
        }

        return beerId;
    }

    public BeerResponseDto findBeerById(Long beerId){
        Beer beer = findBeerEntityById(beerId);
        URL url = getImageUrl(beerId);
        List<String> hashTagList = getHashTagNamesFromBeer(beer);
        return buildBeerResponseDto(beer, url, hashTagList);
    }

    public List<BeerListResponseDto> findAllBeer(){
        List<Beer> all = beerRepository.findAll();
        List<BeerListResponseDto> beerListResponseDtoList = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            Beer beer = all.get(i);

            BeerListResponseDto beerListResponseDto = BeerListResponseDto.builder()
                    .beerName(beer.getBeerName())
                    .hashTagList(getHashTagNamesFromBeer(beer))
                    .likeNum(0)
                    .imageUrl(getImageUrl(beer.getId()).toString())
                    .build();

            beerListResponseDtoList.add(beerListResponseDto);

        }

        return beerListResponseDtoList;

    }

    private Beer findBeerEntityById(Long beerId) {
        return beerRepository.findById(beerId).orElseThrow(() -> new IllegalArgumentException("해당 아이디의 맥주정보를 찾을 수 없습니다."));
    }

    private List<String> getHashTagNamesFromBeer(Beer beer) {
        List<String> hashTagList = new ArrayList<>();
        for (BeerHashTag beerHashTag : beer.getBeerHashTagList()) {
            hashTagList.add(beerHashTag.getHashTag().getTagName());
        }
        return hashTagList;
    }

    private BeerResponseDto buildBeerResponseDto(Beer beer, URL url, List<String> hashTagList) {
        return BeerResponseDto.builder()
                .beerId(beer.getId())
                .beerName(beer.getBeerName())
                .introduction(beer.getIntroduction())
                .content(beer.getContent())
                .hashTagNames(hashTagList)
                .imageUrl(url.toString())
                .build();
    }

    private URL getImageUrl(Long beerId) {
        return amazonS3Client.getUrl("fh-image-bucket", Long.toString(beerId) + "_image.png");
    }

    private List<String> addHashTagsToBeer(BeerSaveRequestDto beerSaveRequestDto, Beer beer) {
        List<String> hashTagList = new ArrayList<>();
        for(Long hashTagId : beerSaveRequestDto.getHashTagIds()){
            HashTag hashTag = hashTagRepository.findById(hashTagId)
                    .orElseThrow(() -> new IllegalArgumentException("해시태그를 찾을 수 없습니다. ID = " + hashTagId));
            BeerHashTag beerHashTag = new BeerHashTag(beer, hashTag);
            beer.addBeerHashTag(beerHashTag);
            hashTag.addBeerHashTag(beerHashTag);
            hashTagList.add(hashTag.getTagName());
            beerHashTagRepository.save(beerHashTag);
        }
        return hashTagList;
    }

    private void uploadImage(MultipartFile file, Beer beer, Long beerId) {
        try {
            String imageFileName = beerId + "_" + file.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, imageFileName, file.getInputStream(), metadata);

            BeerImage image = BeerImage.builder()
                    .url("/beerImages/" + imageFileName)
                    .beer(beer)
                    .build();

            beerImageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int plusLikesCnt(Long beerId) {
        return beerRepository.plusLikesCnt(beerId);
    }

    public int minusLikesCnt(Long beerId) {
        return beerRepository.minusLikesCnt(beerId);
    }

}
