package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, QuerydslPredicateExecutor<Movie> {

}
