package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.interaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.Interaction;

@Repository
public interface InteractionRepository
        extends JpaRepository<Interaction, Long>, QuerydslPredicateExecutor<Interaction> {

    public Interaction findByUserIdAndMovieIdAndIsCurrent(Long userId, Long movieId, boolean isCurrent);
}
