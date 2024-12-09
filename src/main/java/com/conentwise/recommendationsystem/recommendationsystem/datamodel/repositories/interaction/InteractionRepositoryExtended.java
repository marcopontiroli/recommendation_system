package com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.interaction;

import java.util.List;

import org.springframework.stereotype.Service;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.Interaction;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.QInteraction;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.QMovie;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class InteractionRepositoryExtended {

    @PersistenceContext
    private EntityManager entityManager;

    private static final QInteraction QI = QInteraction.interaction;
    private static final QMovie QM = QMovie.movie;

    /**
     * Retrieves a list of interactions for a user filtered by interaction type. The
     * interactions are ordered from the last to the first.
     * 
     * @param userId the ID of the user whose interactions are to be retrieved
     * @param type   the type of interactions to filter by; if null, all
     *               interactions are retrieved
     * @return a list of Interactions representing the user's interactions
     */
    public List<Interaction> findAllInteractionsByUserAndType(Long userId, EInteractionType type) {

        BooleanBuilder condition = new BooleanBuilder(QI.user.id.eq(userId));

        if (type != null) {
            condition.and(QI.type.eq(type));
        }

        return new JPAQuery<Interaction>(entityManager)
                .select(QI)
                .from(QI)
                .join(QI.movie, QM)
                .where(condition)
                .orderBy(QI.date.desc())
                .distinct()
                .fetch();
    }
}
