package com.gdsc.festivalholic.domain.beerImage;

import com.gdsc.festivalholic.domain.beer.Beer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeerImageRepository extends JpaRepository<BeerImage, Long> {

    @Query("select b from BeerImage b where b.beer = :beer")
    List<BeerImage> findByBeer(@Param("beer") Beer beer);

}
