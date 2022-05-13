package com.movie.manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmDto {

    private Long id;

    private String title;

    private String posterUrl;

    private LocalDate releaseDate;

    private String plot;
}
