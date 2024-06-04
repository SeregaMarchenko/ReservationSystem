package com.example.reservationsystem.security.repository;

import com.example.reservationsystem.security.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity,Long> {
    Optional<UserSecurity> findByUserUsername(String username);
    Optional<UserSecurity> findByUsername(String username);
}
