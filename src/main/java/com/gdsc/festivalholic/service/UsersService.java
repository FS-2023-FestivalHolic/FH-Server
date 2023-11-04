package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.beer.BeerLikesResponseDto;
import com.gdsc.festivalholic.controller.dto.login.LoginRequest;
import com.gdsc.festivalholic.controller.dto.user.UsersResponseDto;
import com.gdsc.festivalholic.controller.dto.user.UsersSaveRequestDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.likes.Likes;
import com.gdsc.festivalholic.domain.likes.LikesRepository;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;
    private final LikesRepository likesRepository;
    private final BeerService beerService;

    public Long save(UsersSaveRequestDto usersSaveRequestDto) {
        Users users = usersSaveRequestDto.toEntity();
        Long usersId = usersRepository.save(users).getId();
        return usersId;
    }

    @Transactional(readOnly = true)
    public UsersResponseDto findById(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));

        ArrayList<Likes> likesList = likesRepository.findByUsers(users);
        ArrayList<BeerLikesResponseDto> beerList = new ArrayList<>();

        for (int i=0; i<likesList.size(); i++) {
            Beer beer = likesList.get(i).getBeer();
            beerList.add(new BeerLikesResponseDto(beer.getId(), beer.getBeerName(), beerService.getImageUrl(beer.getId()) , beer.getLikesCnt()));
        }

        UsersResponseDto usersResponseDto = UsersResponseDto.builder()
                .userId(users.getId())
                .userName(users.getName())
                .beerList(beerList)
                .build();

        return usersResponseDto;
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return usersRepository.existsByLoginId(loginId);
    }

    public Users login(LoginRequest loginRequest) {
        Optional<Users> optionalUsers = usersRepository.findByLoginId(loginRequest.getLoginId());

        if(optionalUsers.isEmpty()) {
            return null;
        }
        Users users = optionalUsers.get();

        if(!users.getPassword().equals(loginRequest.getPassword())){
            return null;
        }

        return users;
    }

}
