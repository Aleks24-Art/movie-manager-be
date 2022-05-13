package com.movie.manager.mapper;

import com.movie.manager.dto.FilmDetailsDto;
import com.movie.manager.dto.FilmDto;
import com.movie.manager.dto.FilmMemberDto;
import com.movie.manager.entity.Country;
import com.movie.manager.entity.CreatorRole;
import com.movie.manager.entity.Film;
import com.movie.manager.entity.FilmCreator;
import com.movie.manager.entity.FilmMember;
import com.movie.manager.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    FilmDto toDto(Film film);

    Film toFilm(FilmDto dto);

    FilmMemberDto toMemberDto(FilmMember filmMember);

    @Mapping(source = "id", target = "film.id")
    @Mapping(source = "title", target = "film.title")
    @Mapping(source = "posterUrl", target = "film.posterUrl")
    @Mapping(source = "releaseDate", target = "film.releaseDate")
    @Mapping(source = "countries", target = "countries", qualifiedByName = "toCountryNames")
    @Mapping(source = "creators", target = "directBy", qualifiedByName = "toDirectBy")
    @Mapping(source = "roles", target = "topCast", qualifiedByName = "toTopCast")
    @Mapping(source = "plot", target = "film.plot")
    FilmDetailsDto toDetailsDto(Film film);

    @Named("toCountryNames")
    default List<String> toCountryNames(List<Country> countries) {
        return countries.stream().map(Country::getCountry).collect(Collectors.toList());
    }

    @Named("toTopCast")
    default List<FilmMemberDto> toTopCast(List<Role> creators) {
        return creators.stream().map(Role::getActor).distinct()
                .map(this::toMemberDto).collect(Collectors.toList());
    }

    @Named("toDirectBy")
    default List<FilmMemberDto> toDirectBy(List<FilmCreator> creators) {
        return creators.stream().filter(
                creator -> creator.getCreatorRole().equals(CreatorRole.DIRECTOR)
        ).map(FilmCreator::getCreator).map(this::toMemberDto).collect(Collectors.toList());
    }

    default Film toNewFilm(Film existFilm, Film newFilm) {
        existFilm.setTitle(newFilm.getTitle());
        existFilm.setReleaseDate(newFilm.getReleaseDate());
        existFilm.setPosterUrl(newFilm.getPosterUrl());
        existFilm.setPlot(newFilm.getPlot());
        return existFilm;
    }
}
