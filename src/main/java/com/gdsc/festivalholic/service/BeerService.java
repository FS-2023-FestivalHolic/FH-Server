package com.gdsc.festivalholic.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gdsc.festivalholic.controller.dto.beer.BeerLikeStatusResponse;
import com.gdsc.festivalholic.controller.dto.beer.BeerListResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerResponseDto;
import com.gdsc.festivalholic.controller.dto.beer.BeerSaveRequestDto;
import com.gdsc.festivalholic.controller.dto.beerContent.BeerContentDto;
import com.gdsc.festivalholic.controller.dto.beerImage.BeerImageUploadDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beer.BeerRepository;
import com.gdsc.festivalholic.domain.beerContent.BeerContent;
import com.gdsc.festivalholic.domain.beerContent.BeerContentRepository;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTagRepository;
import com.gdsc.festivalholic.domain.beerImage.BeerImage;
import com.gdsc.festivalholic.domain.beerImage.BeerImageRepository;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.hashTag.HashTagRepository;
import com.gdsc.festivalholic.domain.likes.Likes;
import com.gdsc.festivalholic.domain.likes.LikesRepository;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import com.gdsc.festivalholic.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class BeerService {

    private final BeerRepository beerRepository;
    private final HashTagRepository hashTagRepository;
    private final BeerHashTagRepository beerHashTagRepository;
    private final BeerImageRepository beerImageRepository;
    private final BeerContentRepository beerContentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;
    private final LikesRepository likesRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Long create(BeerSaveRequestDto beerSaveRequestDto, BeerImageUploadDto beerImageUploadDto) {
        Beer beer = beerSaveRequestDto.toEntity();
        MultipartFile file = beerImageUploadDto.getFile();

        addHashTagsToBeer(beerSaveRequestDto, beer);
        addBeerContentToBeer(beerSaveRequestDto, beer);
        Long beerId = beerRepository.save(beer).getId();

        // 이미지 부분
        if (file != null && !file.isEmpty()) {
            uploadImage(file, beer, beerId);
        }

        return beerId;
    }

    public Long update(Long id, BeerImageUploadDto beerImageUploadDto){
        MultipartFile file = beerImageUploadDto.getFile();
        // id로 beer찾기
        Beer beer = beerRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 맥주가 존재하지 않습니다."));

        if (file != null && !file.isEmpty()){
            uploadImage(file, beer, id);
        }

        return id;
    }

    public BeerResponseDto findBeerById(Long beerId){
        Beer beer = findBeerEntityById(beerId);
        URL url = getImageUrl(beerId);
        List<BeerContentDto> beerContentDtoList = getBeerContentDtoList(beer);
        List<String> hashTagList = getHashTagNamesFromBeer(beer);
        return buildBeerResponseDto(beer, url, beerContentDtoList, hashTagList);
    }

    public BeerLikeStatusResponse findBeerLikeStatusByToken(String token, Long beerId){
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String loginId = authentication.getName();

        Users users = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + loginId));

        Beer beer = beerRepository.findById(beerId)
                        .orElseThrow(() -> new IllegalArgumentException(("해당 맥주가 없습니다. id=") + beerId));

        Optional<Likes> byUsersAndBeer = likesRepository.findByUsersAndBeer(users, beer);

        BeerLikeStatusResponse beerLikeStatusResponse = new BeerLikeStatusResponse();
        if (byUsersAndBeer.isPresent()){
            beerLikeStatusResponse.like();
        }

        return beerLikeStatusResponse;
    }

    public List<BeerListResponseDto> findAllBeerByOrderByLikesCnt(){
        List<Beer> all = beerRepository.findAllByOrderByLikesCntDesc();
        return convertToBeerListResponseDto(all);
    }

    public List<BeerListResponseDto> findAllBeerByOrderByBeerName(){
        List<Beer> all = beerRepository.findAllByOrderByBeerNameAsc();
        return convertToBeerListResponseDto(all);
    }

    public List<BeerListResponseDto> findAllBeer(){
        List<Beer> all = beerRepository.findAll();
        return convertToBeerListResponseDto(all);
    }

    public List<BeerListResponseDto> findBeersByHashTags(List<Long> hashTags) {
        List<Beer> beers = beerRepository.findByHashTagIds(hashTags);
        return convertToBeerListResponseDto(beers);
    }

    private List<BeerListResponseDto> convertToBeerListResponseDto(List<Beer> beers){
        List<BeerListResponseDto> beerListResponseDtoList = new ArrayList<>();
        for (int i = 0; i < beers.size(); i++) {
            Beer beer = beers.get(i);

            BeerListResponseDto beerListResponseDto = BeerListResponseDto.builder()
                    .beerId(beer.getId())
                    .beerName(beer.getBeerName())
                    .hashTagList(getHashTagNamesFromBeer(beer))
                    .likesCnt(beer.getLikesCnt())
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

    private List<BeerContentDto> getBeerContentDtoList(Beer beer){
        List<BeerContentDto> beerContentDtoList = new ArrayList<>();
        for(BeerContent beerContent : beer.getBeerContentList()){
            BeerContentDto beerContentDto = beerContent.toDto(beerContent);
            beerContentDtoList.add(beerContentDto);
        }
        return beerContentDtoList;
    }

    private BeerResponseDto buildBeerResponseDto(Beer beer, URL url, List<BeerContentDto> beerContentDtoList, List<String> hashTagList) {
        return BeerResponseDto.builder()
                .beerId(beer.getId())
                .beerName(beer.getBeerName())
                .likesCnt(beer.getLikesCnt())
                .introduction(beer.getIntroduction())
                .beerContentList(beerContentDtoList)
                .hashTagNames(hashTagList)
                .imageUrl(url.toString())
                .build();
    }

    public URL getImageUrl(Long beerId) {
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

    private void addBeerContentToBeer(BeerSaveRequestDto beerSaveRequestDto, Beer beer){
        for(BeerContentDto beerContentDto : beerSaveRequestDto.getBeerContentDtoList()){
            String subject = beerContentDto.getSubject();
            String description = beerContentDto.getDescription();
            System.out.println("addBeerContentToBeer : " + beer.getBeerName());

            BeerContent build = BeerContent.builder()
                    .subject(subject)
                    .description(description)
                    .beer(beer)
                    .build();

            beer.addBeerContent(build);
            beerContentRepository.save(build);
        }
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
