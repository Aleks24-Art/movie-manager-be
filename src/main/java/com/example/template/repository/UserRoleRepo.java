package com.example.template.repository;

import com.example.template.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByName(String name);
}
