package com.movie.manager.repository;

import com.movie.manager.entity.FilmMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmMemberRepository extends JpaRepository<FilmMember, Long> {
}
