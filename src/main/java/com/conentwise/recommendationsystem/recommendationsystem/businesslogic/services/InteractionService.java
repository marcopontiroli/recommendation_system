package com.conentwise.recommendationsystem.recommendationsystem.businesslogic.services;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction.Interaction;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.Movie;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.user.User;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.ERatingType;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.interaction.InteractionRepository;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.interaction.InteractionRepositoryExtended;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.movie.MovieRepository;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.user.UserRepository;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionDTO;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionInsertDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InteractionService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private InteractionRepositoryExtended interactionRepositoryExtended;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    Validator validator;

    /**
     * Retrieves a list of user interactions filtered by interaction type.
     *
     * @param userId the ID of the user whose interactions are to be retrieved
     * @param type   the type of interactions to filter by; if null, all
     *               interactions are retrieved
     * @return a list of InteractionDTOs representing the user's interactions
     */
    public List<InteractionDTO> getUserInteractions(Long userId, EInteractionType type) {
        log.info("Fetching interaction history for user: {} with type filter: {}", userId, type);

        List<Interaction> interactions = interactionRepositoryExtended.findAllInteractionsByUserAndType(userId, type);

        return interactions.stream().map(i -> modelMapper.map(i, InteractionDTO.class)).toList();

    }

    /**
     * Adds an interaction for a given user and movie id. The interaction can be
     * either a rating or a view interaction.
     * The type in the DTO is used to determine if it's a rating or a view
     * interaction. Populate rating or viewPercentage
     * accordingly. The interaction are saved to the database but not overridden.
     * The value isCurrent indicates whether the interaction is the most significant
     * for that specific movie
     *
     * @param userId         the user id
     * @param movieId        the movie id
     * @param interactionDTO the interaction to be added.
     * @return The added interaction as a DTO.
     */
    public InteractionDTO addInteraction(Long userId, Long movieId, InteractionInsertDTO interactionDTO) {
           
        if (validator.validateObject(interactionDTO).hasErrors()) {
            throw new IllegalArgumentException("Invalid interaction data");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + movieId + " not found"));

        log.info("Adding interaction for user: {} on movie: {} with type: {}, rating: {}, view percentage: {}", userId,
                movieId, interactionDTO.getType(), interactionDTO.getRating(), interactionDTO.getViewPercentage());

        Interaction interaction = modelMapper.map(interactionDTO, Interaction.class);
        Interaction lastCurrentInteraction = interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true);

        if (interactionDTO.getType().equals(EInteractionType.RATING)) {
            interaction.setRatingType(ERatingType.EXPLICIT);
            interaction.setViewPercentage(null);
            // if it's a rating, we consider the last one saved ang give priority over the
            // implicit rating
            interaction.setIsCurrent(true);

            if (lastCurrentInteraction != null) {
                lastCurrentInteraction.setIsCurrent(false);
                interactionRepository.save(lastCurrentInteraction);
            }

        } else {
            interaction.setRatingType(ERatingType.IMPLICIT);
            interaction.setRating(Math.min(interactionDTO.getViewPercentage() / 20 + 1, 5));
            interaction.setIsCurrent(false);
            if (lastCurrentInteraction == null) {
                interaction.setIsCurrent(true);
            } else if (lastCurrentInteraction.getRatingType().equals(ERatingType.IMPLICIT)) {
                // I'm assuming that to update the current implicit rating the API has to pass a
                // view percentage greater than the greatest view percentage for that movie and
                // that user. The implicit rating cannot go down this way.

                if (interactionDTO.getViewPercentage() > lastCurrentInteraction.getViewPercentage()) {

                    lastCurrentInteraction.setIsCurrent(false);
                    interactionRepository.save(lastCurrentInteraction);

                    interaction.setIsCurrent(true);
                }
            }

        }
        interaction.setDate(LocalDateTime.now());
        interaction.setUser(user);
        interaction.setMovie(movie);
        Interaction savedInteraction = interactionRepository.save(interaction);

        InteractionDTO result = modelMapper.map(savedInteraction, InteractionDTO.class);
        return result;
    }
}