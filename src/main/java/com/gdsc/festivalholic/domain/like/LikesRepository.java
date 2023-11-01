package com.gdsc.festivalholic.domain.like;

import com.gdsc.festivalholic.domain.beer.Beer;
import com.gdsc.festivalholic.domain.users.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l where l.users = :users and l.beer = :beer")
    Optional<Likes> findByUsersAndBeer(@Param("users") Users users, @Param("beer")Beer beer);


}
