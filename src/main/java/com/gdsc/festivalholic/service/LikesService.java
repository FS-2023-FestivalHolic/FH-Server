package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.likes.LikesRequestDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.beer.BeerRepository;
import com.gdsc.festivalholic.domain.like.Likes;
import com.gdsc.festivalholic.domain.like.LikesRepository;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UsersRepository usersRepository;
    private final BeerRepository beerRepository;
    private final BeerService beerService;

    public Long insert(LikesRequestDto likesRequestDto) {
        Users users = usersRepository.findById(likesRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + likesRequestDto.getUserId()));
        Beer beer = beerRepository.findById(likesRequestDto.getBeerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 맥주가 없습니다. id=" + likesRequestDto.getBeerId()));

        if(likesRepository.findByUsersAndBeer(users, beer).isPresent()) {
            throw new RuntimeException("이미 좋아요 상태입니다.");
        }

        Likes likes = Likes.builder()
                .beer(beer)
                .users(users)
                .build();

        Long likesId = likesRepository.save(likes).getId();
        beerService.plusLikesCnt(beer.getId());
        return likesId;
    }

    public void delete(Long likesId) {
        Likes likes = likesRepository.findById(likesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다. id=" + likesId));

        beerService.minusLikesCnt(likes.getBeer().getId());
        likesRepository.delete(likes);
    }

}
