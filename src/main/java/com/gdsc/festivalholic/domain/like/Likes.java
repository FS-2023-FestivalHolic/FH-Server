package com.gdsc.festivalholic.domain.like;

import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.users.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beerId")
    private Beer beer;

    public void setUsers(Users users) {
        this.users = users;
        users.getLikeList().add(this);
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
        beer.getLikeList().add(this);
    }

    @Builder
    public Likes(Users users, Beer beer) {
        this.setBeer(beer);
        this.setUsers(users);
    }

}
