package com.example.template.service;

import com.example.template.entity.Film;
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
