package com.gdsc.festivalholic.domain.beer;

import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerImage.BeerImage;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import com.gdsc.festivalholic.domain.likes.Likes;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;


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
    private List<Likes> likesList = new ArrayList<>();

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
