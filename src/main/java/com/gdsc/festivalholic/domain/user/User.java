package com.gdsc.festivalholic.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    private String name;
    private String nickName;

    @Builder
    public User(String loginId, String password, String email, String name, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }
}
