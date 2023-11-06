package com.gdsc.festivalholic.domain.beerHashTag;

import com.gdsc.festivalholic.domain.hashTag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BeerHashTagRepository extends JpaRepository<BeerHashTag, Long> {

    List<BeerHashTag> findByHashTagIn(List<Long> hashTags);
}
