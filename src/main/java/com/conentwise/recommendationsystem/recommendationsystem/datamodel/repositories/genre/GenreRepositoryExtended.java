package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.genre;

import java.util.List;

import org.springframework.stereotype.Service;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.genre.QGenre;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.QInteraction;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.QMovie;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GenreRepositoryExtended {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QGenre QG = QGenre.genre;
    private static final QMovie QM = QMovie.movie;
    private static final QInteraction QI = QInteraction.interaction;

    /**
     * Finds all genres that are preferred by a user, with a rating >= 4.
     * 
     * @param userId the ID of the user for which preferred genres are requested
     * @return a list of preferred genres
     */
    public List<String> findPreferredGenresForUser(Long userId) {

        return new JPAQuery<String>(entityManager)
                .select(QG.name)
                .from(QI)
                .join(QI.movie, QM)
                .join(QM.genres, QG)
                .where(QI.user.id.eq(userId)
                        .and(QI.rating.goe(4))
                        .and(QI.isCurrent.isTrue()))
                .distinct()
                .fetch();
    }

}
