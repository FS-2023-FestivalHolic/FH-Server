package com.gdsc.festivalholic.domain.beer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, Long> {

    @Modifying
    @Query("update Beer b set b.likesCnt = b.likesCnt + 1 where b.id = :id")
    int plusLikesCnt(@Param("id") Long id);

    @Modifying
    @Query("update Beer b set b.likesCnt = b.likesCnt - 1 where b.id = :id")
    int minusLikesCnt(@Param("id") Long id);

    List<Beer> findAllByOrderByLikesCntDesc();

    List<Beer> findAllByOrderByBeerNameAsc();

    @Query("select b from Beer b join b.beerHashTagList bh where bh.hashTag.id in :hashTagIds")
    List<Beer> findByHashTagIds(@Param("hashTagIds") List<Long> hashTagIds);

}

