package com.movie.manager.controller;


import com.movie.manager.dto.FilmDetailsDto;
import com.movie.manager.dto.FilmDto;
import com.movie.manager.entity.Film;
import com.movie.manager.mapper.FilmMapper;
import com.movie.manager.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmMapper mapper;

    @GetMapping(params = {"page", "size"})
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public Page<FilmDto> findPaginated(@RequestParam("page") int page,
                                       @RequestParam("size") int size) {
        return filmService.findPaginated(page, size).map(mapper::toDto);
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_MANAGER')")
    public FilmDto addFilm(@RequestBody FilmDto dto) {
        Film savedFilm = filmService.saveFilm(mapper.toFilm(dto));
        return mapper.toDto(savedFilm);
    }

    @PutMapping
    @PreAuthorize(value = "hasAuthority('ROLE_MANAGER')")
    public FilmDto updateFilm(@RequestBody FilmDto dto) {
        Film savedFilm = filmService.updateFilm(mapper.toFilm(dto), dto.getId());
        return mapper.toDto(savedFilm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public FilmDetailsDto getFilmDetails(@PathVariable Long id) {
        return mapper.toDetailsDto(filmService.getFilmDetails(id));
    }

    @GetMapping("/search/{title}")
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public Page<FilmDto> searchFilms(@PathVariable String title,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        return filmService.searchFilms(page, size, title).map(mapper::toDto);
    }
}
