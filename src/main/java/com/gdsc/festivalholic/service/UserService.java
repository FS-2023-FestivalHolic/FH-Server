package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.contorller.dto.user.UserResponseDto;
import com.gdsc.festivalholic.contorller.dto.user.UserSaveRequestDto;
import com.gdsc.festivalholic.domain.user.User;
import com.gdsc.festivalholic.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Long save(UserSaveRequestDto userSaveRequestDto) {
        User user = userSaveRequestDto.toEntity();
        Long userId = userRepository.save(user).getId();
        return userId;
    }

//    @Transactional(readOnly = true)
//    public UserResponseDto findById(Long userId) {
//
//    }
}
