package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.movie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.genre.QGenre;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.QInteraction;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.Movie;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.QMovie;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MovieRepositoryExtended {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QMovie QM = QMovie.movie;
    private static final QInteraction QI = QInteraction.interaction;
    private static final QGenre QG = QGenre.genre;

    /**
     * Finds all movies with optional filters of genre, avg min rating and avg max rating.
     * If no filters are provided, all movies are returned.
     * 
     * @param genres   list of genres to filter movies by
     * @param minRating avg minimum rating value to filter movies by
     * @param maxRating avg maximum rating value to filter movies by
     * @return List of movies with the given filters
     */
    public List<Movie> findAllMovies(List<String> genres, Integer minRating, Integer maxRating) {
        JPAQuery<Movie> query = new JPAQuery<>(entityManager)
                .select(QM)
                .from(QM)
                .leftJoin(QM.genres, QG)
                .leftJoin(QI).on(QI.movie.id.eq(QM.id));

        BooleanBuilder whereCondition = new BooleanBuilder();
        if (genres != null) {
            BooleanBuilder genreCondition = new BooleanBuilder();
            genres.forEach(genre -> genreCondition.or(QG.name.eq(genre)));
            whereCondition.and(genreCondition);
        }

        if (minRating != null || maxRating != null) {
            whereCondition.and(QI.isCurrent.isTrue());
            query.where(whereCondition);
            query.groupBy(QM.id);
            query.having(
                    QI.rating.avg().goe(minRating == null ? 0 : minRating)
                            .and(QI.rating.avg().loe(maxRating == null ? 5 : maxRating)));
        } else {
            query.where(whereCondition);
        }

        return query.fetch();
    }

    /**
     * Finds all movies that match the user's preferred genres and that the user has
     * not interacted with yet. The movies are ordered by the number of total interactions
     * they have.
     * 
     * @param userId           the ID of the user for which the movies are to be
     *                          recommended
     * @param preferredGenres  the list of preferred genres of the user
     * @return a list of movies that match the user's preferences and that the user
     *         has not interacted with yet
     */
    public List<Movie> findAllRecommendMoviesForUser(Long userId, List<String> preferredGenres) {

        return new JPAQuery<Movie>(entityManager)
                .select(QM)
                .from(QM)
                .join(QM.genres, QG)
                .where(QG.name.in(preferredGenres))
                .where(QM.id.notIn(
                        JPAExpressions.select(QI.movie.id)
                                .from(QI)
                                .where(QI.user.id.eq(userId))))
                .distinct()
                .orderBy(QM.interactions.size().desc())
                .fetch();

    }

}
