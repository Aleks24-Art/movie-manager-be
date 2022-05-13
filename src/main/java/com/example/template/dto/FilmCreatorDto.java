package com.example.template.dto;

import com.example.template.entity.CreatorRole;
import lombok.Data;

@Data
public class FilmCreatorDto {

    private Long id;

    private CreatorRole creatorRole;

    private FilmMemberDto creator;
}
