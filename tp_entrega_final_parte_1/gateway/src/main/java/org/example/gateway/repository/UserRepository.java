package org.example.gateway.repository;

import org.example.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        FROM User u JOIN FETCH u.authorities
        WHERE lower(u.username) =  ?1
    """)
    Optional<User> findOneWithAuthoritiesByUsernameIgnoreCase(String username );
}
