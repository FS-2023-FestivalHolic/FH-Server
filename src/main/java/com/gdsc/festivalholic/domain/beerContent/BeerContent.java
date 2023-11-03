package com.gdsc.festivalholic.domain.beerContent;

import com.gdsc.festivalholic.controller.dto.beerContent.BeerContentDto;
import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.hashTag.HashTag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class BeerContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @Builder
    public BeerContent(String subject, String description, Beer beer) {
        this.subject = subject;
        this.description = description;
        this.beer = beer;
    }

    public BeerContentDto toDto(BeerContent beerContent){
        return BeerContentDto.builder()
                .subject(beerContent.getSubject())
                .description(beerContent.getDescription())
                .build();
    }



}
