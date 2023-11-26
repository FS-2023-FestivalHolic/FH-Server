package com.gdsc.festivalholic.domain.beer;

import com.gdsc.festivalholic.domain.beerContent.BeerContent;
import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import com.gdsc.festivalholic.domain.beerImage.BeerImage;
import com.gdsc.festivalholic.domain.likes.Likes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "beer")
    private List<BeerContent> beerContentList = new ArrayList<>();

    @OneToMany(mappedBy = "beer")
    private List<BeerImage> beerImage;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer likesCnt;

    @OneToMany(mappedBy = "beer")
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public Beer(String beerName, String introduction, List<BeerContent> beerContentList, Integer likesCnt) {
        this.beerName = beerName;
        this.introduction = introduction;
        this.beerContentList = beerContentList;
        this.likesCnt = likesCnt;
    }

    @OneToMany(mappedBy = "beer")
    private List<BeerHashTag> beerHashTagList = new ArrayList<>();

    public void addBeerHashTag(BeerHashTag beerHashTag) {
        this.beerHashTagList.add(beerHashTag);
    }

    public void addBeerContent(BeerContent beerContent) { this.beerContentList.add(beerContent);}
}
