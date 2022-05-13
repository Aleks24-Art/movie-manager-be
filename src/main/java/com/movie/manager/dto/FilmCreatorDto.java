package com.movie.manager.dto;

import com.movie.manager.entity.CreatorRole;
import lombok.Data;

@Data
public class FilmCreatorDto {

    private Long id;

    private CreatorRole creatorRole;

    private FilmMemberDto creator;
}
