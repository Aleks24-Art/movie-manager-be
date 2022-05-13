package com.movie.manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmMemberDto {

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String nationality;
}
