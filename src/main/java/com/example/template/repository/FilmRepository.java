package com.example.template.repository;

import com.example.template.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
