package com.example.demo.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByusername(String username);
    Optional<SiteUser> findByEmail(String email);


    @Transactional
    @Query("UPDATE SiteUser u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(Long userId, String password);

    SiteUser findByUsernameAndEmail(String username, String email);

    SiteUser findByUsername(String username);
}
