package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.controller.dto.user.UsersSaveRequestDto;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import com.gdsc.festivalholic.domain.users.Users;
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

    public Long save(UsersSaveRequestDto usersSaveRequestDto) {
        Users users = usersSaveRequestDto.toEntity();
        Long usersId = usersRepository.save(users).getId();
        return usersId;
    }

//    @Transactional(readOnly = true)
//    public UserResponseDto findById(Long userId) {
//
//    }
}
