package com.movie.manager.repository;

import com.movie.manager.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
