package com.gdsc.festivalholic.controller;

import com.gdsc.festivalholic.config.exception.ApiException;
import com.gdsc.festivalholic.config.exception.ErrorCode;
import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.user.UsersResponseDto;
import com.gdsc.festivalholic.controller.dto.user.UsersSaveRequestDto;
import com.gdsc.festivalholic.service.UsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService userService;

    //회원가입   -> 나중에 아이디, 이름 등 pattern 예외처리 추가
    @PostMapping("sign-up")
    public ResponseDto<Long> save(@RequestBody UsersSaveRequestDto requestDto) {
        if(requestDto.getLoginId() == null || requestDto.getLoginId().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_LOGIN_ID);
        if(requestDto.getPassword() == null || requestDto.getPassword().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_PASSWORD);
        if(requestDto.getEmail() == null || requestDto.getEmail().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_EMAIL);
        if(requestDto.getName() == null || requestDto.getName().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_NAME);
        if(requestDto.getNickName() == null || requestDto.getNickName().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_NICKNAME);

        Long userId = userService.save(requestDto);
        return ResponseUtil.SUCCESS("회원가입에 성공하였습니다.", userId);
    }

    //내 정보 조회
    @GetMapping("/{userId}")
    public ResponseDto<UsersResponseDto> findById(@PathVariable("userId") Long userId) {
        return ResponseUtil.SUCCESS("내 정보 조회에 성공하였습니다.", userService.findById(userId));
    }

}
