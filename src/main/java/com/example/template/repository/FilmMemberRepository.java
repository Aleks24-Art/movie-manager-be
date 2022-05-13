package com.example.template.repository;

import com.example.template.entity.FilmMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmMemberRepository extends JpaRepository<FilmMember, Long> {
}
