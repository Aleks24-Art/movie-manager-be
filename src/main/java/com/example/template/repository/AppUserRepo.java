package com.example.template.repository;

import com.example.template.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    void deleteByUsername(String username);
}
