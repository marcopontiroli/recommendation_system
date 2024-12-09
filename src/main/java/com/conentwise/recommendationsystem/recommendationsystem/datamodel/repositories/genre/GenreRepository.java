package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.genre.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, QuerydslPredicateExecutor<Genre>{}