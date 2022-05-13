package com.movie.manager.repository;

import com.movie.manager.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByName(String name);
}
