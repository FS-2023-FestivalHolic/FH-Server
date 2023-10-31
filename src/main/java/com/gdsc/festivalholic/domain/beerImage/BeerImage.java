package com.gdsc.festivalholic.domain.beerImage;

import com.gdsc.festivalholic.domain.beer.Beer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class BeerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @Builder
    public BeerImage (String url, Beer beer){
        this.url = url;
        this.beer = beer;
    }
}
