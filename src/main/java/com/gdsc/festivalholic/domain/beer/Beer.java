package com.gdsc.festivalholic.domain.beer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beer_name;

    private String introduction;

    private String content;

    @Builder
    public Beer(String beer_name, String introduction, String content) {
        this.beer_name = beer_name;
        this.introduction = introduction;
        this.content = content;
        
    }
}
