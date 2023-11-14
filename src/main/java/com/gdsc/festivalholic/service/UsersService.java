package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.SessionManager;
import com.gdsc.festivalholic.controller.dto.beer.BeerLikesResponseDto;
import com.gdsc.festivalholic.controller.dto.login.LoginRequest;
import com.gdsc.festivalholic.controller.dto.login.SessionIdDto;
import com.gdsc.festivalholic.controller.dto.user.UsersResponseDto;
import com.gdsc.festivalholic.controller.dto.user.UsersSaveRequestDto;
import com.gdsc.festivalholic.domain.TokenInfo;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.likes.Likes;
import com.gdsc.festivalholic.domain.likes.LikesRepository;
import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import com.gdsc.festivalholic.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;



    @Autowired
    private final SessionManager sessionManager;

    public Long save(UsersSaveRequestDto usersSaveRequestDto) {
        Users users = usersSaveRequestDto.toEntity();
        Long usersId = usersRepository.save(users).getId();
        return usersId;
    }

    @Transactional(readOnly = true)
    public UsersResponseDto findById(String token) {

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String loginId = authentication.getName();

        Users users = usersRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + loginId));

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

    public SessionIdDto login(LoginRequest loginRequest, HttpServletResponse response) {
        Optional<Users> optionalUsers = usersRepository.findByLoginId(loginRequest.getLoginId());

        if(optionalUsers.isEmpty()) {
            throw new IllegalArgumentException("로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }
        Users users = optionalUsers.get();

        if(!users.getPassword().equals(loginRequest.getPassword())){
            throw new IllegalArgumentException("로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        String sessionId = sessionManager.createSession(users, response);
        SessionIdDto sessionIdDto = SessionIdDto.builder().sessionId(sessionId).build();

        return sessionIdDto;
    }

    public void logout(HttpServletRequest request) {
        sessionManager.expire(request);
    }

    public Users getUserBySessionId(HttpServletRequest request) {
        Users users = (Users) sessionManager.getSession(request);
        if(users == null) {
            throw new RuntimeException("로그인 해주세요.");
        }
        return users;
    }

    public Users getUserByStringSessionId(String header){
        Users userInfo = (Users) sessionManager.getUserInfo(header);
        return userInfo;
    }

    public SessionIdDto login2(String loginId, String password, HttpServletResponse httpServletResponse) {

        Optional<Users> optionalUsers = usersRepository.findByLoginId(loginId);

        Users users = optionalUsers.get();

        String sessionId = sessionManager.createSession(users, httpServletResponse);
        SessionIdDto sessionIdDto = SessionIdDto.builder().sessionId(sessionId).build();

        return sessionIdDto;

    }

    public TokenInfo login3(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}