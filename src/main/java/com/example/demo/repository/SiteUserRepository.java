package com.example.demo.repository;

import com.example.demo.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, String> {
    Optional<SiteUser> findByUsername(String username);
    Optional<SiteUser> findByUsernameAndPassword(String username, String password);

}
