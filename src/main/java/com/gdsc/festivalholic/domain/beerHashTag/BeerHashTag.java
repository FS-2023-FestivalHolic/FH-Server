package com.gdsc.festivalholic.domain.beerHashTag;

import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class BeerHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    public BeerHashTag(Beer beer, HashTag hashTag) {
        this.beer = beer;
        this.hashTag = hashTag;
    }

}
