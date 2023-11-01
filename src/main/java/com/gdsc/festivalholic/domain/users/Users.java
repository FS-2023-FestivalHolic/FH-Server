package com.gdsc.festivalholic.domain.users;

import com.gdsc.festivalholic.domain.like.Likes;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Users {

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

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @Builder
    public Users(String loginId, String password, String email, String name, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }
}
