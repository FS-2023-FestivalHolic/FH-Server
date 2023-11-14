package com.gdsc.festivalholic.controller;

import com.gdsc.festivalholic.SessionManager;
import com.gdsc.festivalholic.config.exception.ApiException;
import com.gdsc.festivalholic.config.exception.ErrorCode;
import com.gdsc.festivalholic.config.response.ResponseDto;
import com.gdsc.festivalholic.config.response.ResponseUtil;
import com.gdsc.festivalholic.controller.dto.login.LoginRequest;
import com.gdsc.festivalholic.controller.dto.login.SessionIdDto;
import com.gdsc.festivalholic.controller.dto.user.UsersResponseDto;
import com.gdsc.festivalholic.controller.dto.user.UsersSaveRequestDto;
import com.gdsc.festivalholic.domain.TokenInfo;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.service.LikesService;
import com.gdsc.festivalholic.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@Tag(name = "유저 API", description = "유저")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService userService;

    //회원가입   -> 나중에 아이디, 이름 등 pattern 예외처리 추가
    @PostMapping("/sign-up")
    public ResponseDto<Long> save(@RequestBody UsersSaveRequestDto requestDto) {
        if(requestDto.getLoginId() == null || requestDto.getLoginId().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_LOGIN_ID);
        if(requestDto.getPassword() == null || requestDto.getPassword().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_PASSWORD);
        if(requestDto.getEmail() == null || requestDto.getEmail().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_EMAIL);
        if(requestDto.getName() == null || requestDto.getName().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_NAME);
        if(requestDto.getNickname() == null || requestDto.getNickname().equals(""))
            throw new ApiException(ErrorCode.USER_EMPTY_NICKNAME);
        if(userService.checkLoginIdDuplicate(requestDto.getLoginId()))
            throw new ApiException(ErrorCode.USER_DUPLICATED_LOGIN_ID);

        Long userId = userService.save(requestDto);
        return ResponseUtil.SUCCESS("회원가입에 성공하였습니다.", userId);
    }

    //내 정보 조회
    @GetMapping("")
    public ResponseDto<UsersResponseDto> findById(@RequestHeader("Accesstoken") String accessToken) {

        return ResponseUtil.SUCCESS("내 정보 조회에 성공하였습니다.", userService.findById(accessToken));
    }

    @PostMapping("/login")
    public ResponseDto<TokenInfo> login(@RequestBody LoginRequest loginRequest) {
        return ResponseUtil.SUCCESS("로그인 성공", userService.login3(loginRequest));
    }

    @GetMapping("/login/{loginId}/{password}")
    public ResponseDto<SessionIdDto> login(@PathVariable("loginId") String loginId, @PathVariable("password") String password, HttpServletResponse httpServletResponse) {
        return ResponseUtil.SUCCESS("로그인 성공", userService.login2(loginId, password, httpServletResponse));
    }

    @GetMapping("/logout")
    public ResponseDto<String> logout(HttpServletRequest httpServletRequest) {
        userService.logout(httpServletRequest);
        return ResponseUtil.SUCCESS("로그아웃 완료", "");
    }

}
