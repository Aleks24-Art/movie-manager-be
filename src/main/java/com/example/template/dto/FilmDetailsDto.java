package com.example.template.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilmDetailsDto {

    private FilmDto film;

    private List<String> countries;

    private List<FilmMemberDto> directBy;

    private List<FilmMemberDto> topCast;
}
