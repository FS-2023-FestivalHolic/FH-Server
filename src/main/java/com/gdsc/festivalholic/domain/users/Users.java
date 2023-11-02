package com.gdsc.festivalholic.domain.users;

<<<<<<< HEAD
import com.gdsc.festivalholic.domain.like.Likes;
=======
import com.gdsc.festivalholic.domain.likes.Likes;
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3
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
<<<<<<< HEAD
    private List<Likes> likeList = new ArrayList<>();
=======
    private List<Likes> likesList = new ArrayList<>();
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3

    @Builder
    public Users(String loginId, String password, String email, String name, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }
}
