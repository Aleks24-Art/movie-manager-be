package com.example.template.service.impl;

import com.example.template.entity.Film;
import com.example.template.mapper.FilmMapper;
import com.example.template.repository.FilmRepository;
import com.example.template.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper mapper;

    @Override
    public Page<Film> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return filmRepository.findAll(pageable);
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Film updateFilm(Film newFilm, Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film with id = " + id + " does not exist!"));
        return filmRepository.save(mapper.toNewFilm(film, newFilm));
    }

    @Override
    public void deleteFilmById(Long id) {
        filmRepository.deleteById(id);
    }

    @Override
    public Film getFilmDetails(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film with id = " + id + " does not exist!"));
    }

    @Override
    public Page<Film> searchFilms(int page, int size, String title) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return filmRepository.findByTitleContainingIgnoreCase(title, pageable);
    }


}
