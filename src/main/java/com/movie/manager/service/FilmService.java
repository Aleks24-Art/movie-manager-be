package com.movie.manager.service;

import com.movie.manager.entity.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {

    Page<Film> findPaginated(int page, int size);

    List<Film> findAll();

    Film saveFilm(Film film);

    Film updateFilm(Film film, Long id);

    void deleteFilmById(Long id);

    Film getFilmDetails(Long id);

    Page<Film> searchFilms(int page, int size, String title);
}
