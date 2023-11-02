package com.gdsc.festivalholic.domain.beer;

import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerImage.BeerImage;
<<<<<<< HEAD
import com.gdsc.festivalholic.domain.like.Likes;
=======
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.likes.Likes;
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import org.hibernate.annotations.ColumnDefault;
import org.springframework.core.annotation.Order;
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;


@Getter
@Entity
@NoArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String beerName;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer likesCnt;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<BeerImage> beerImageList;

    @OneToMany(mappedBy = "beer")
<<<<<<< HEAD
    private List<Likes> likeList = new ArrayList<>();
=======
    private List<Likes> likesList = new ArrayList<>();
>>>>>>> b5b8924326e76ad608845dbad68bff3d8b36eee3

    @Builder
    public Beer(String beerName, String introduction, String content) {
        this.beerName = beerName;
        this.introduction = introduction;
        this.content = content;
        this.likesCnt = 0;
    }

    @OneToMany(mappedBy = "beer")
    private List<BeerHashTag> beerHashTagList = new ArrayList<>();

    public void addBeerHashTag(BeerHashTag beerHashTag) {
        this.beerHashTagList.add(beerHashTag);
    }
}
