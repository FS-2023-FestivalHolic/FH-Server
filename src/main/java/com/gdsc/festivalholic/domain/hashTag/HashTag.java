package com.gdsc.festivalholic.domain.hashTag;


import com.gdsc.festivalholic.domain.beerHashTag.BeerHashTag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "hashTag")
    private List<BeerHashTag> beerHashTagList = new ArrayList<>();

    public void addBeerHashTag(BeerHashTag beerHashTag) {
        this.beerHashTagList.add(beerHashTag);
    }

    @Builder
    public HashTag(String tagName){
        this.tagName = tagName;
    }
}
