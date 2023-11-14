package com.gdsc.festivalholic.service;

import com.gdsc.festivalholic.domain.users.Users;
import com.gdsc.festivalholic.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return usersRepository.findByLoginId(loginId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Users user) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(
                user.getLoginId(),
                passwordEncoder.encode(user.getPassword()),
                authorities
        );
    }
}
