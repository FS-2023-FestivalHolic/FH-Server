package com.gdsc.festivalholic.domain.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByLoginId(String loginId);
    Optional<Users> findByLoginId(String loginId);

}
