package com.conentwise.recommendationsystem.recommendationsystem.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conentwise.recommendationsystem.recommendationsystem.businesslogic.services.InteractionService;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionDTO;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionInsertDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/interaction")
@Tag(name = "Interaction API", description = "Operations related to user interactions with movies, including viewing and rating.")
public class InteractionAPI {

    @Autowired
    private InteractionService interactionService;

    /**
     * Fetches the interaction history for a given user id. Full history details are
     * recorded, even old ratings given to a movie that have been overridden.
     *
     * @param userId the user id
     * @param type   the interaction type filter. If not provided, all types of
     *               interactions are fetched.
     * @return A list of interaction DTOs.
     */
    @Operation(summary = "Fetch the interaction history for a given user", description = "Fetches the interaction history for a given user id, including old ratings and overridden interactions. "
            + "You can filter by interaction type (rating, view) or fetch all types if no filter is applied.")
    @GetMapping("/{userId}/")
    public List<InteractionDTO> getUserInteractionHistory(
            @Parameter(description = "The user id for which the interaction history is being fetched", required = true) @PathVariable Long userId,

            @Parameter(description = "The interaction type filter (optional). If not provided, all interaction types will be fetched.", required = false) @RequestParam(required = false) EInteractionType type) {

        log.info("Received request to fetch interaction history for user: {} with filter: {}", userId, type);
        return interactionService.getUserInteractions(userId, type);
    }

    /**
     * Adds an interaction for a given user and movie id.
     *
     * @param userId         the user id
     * @param movieId        the movie id
     * @param interactionDTO the interaction to be added. The type in the DTO is
     *                       used to determine if it's a rating or a view
     *                       interaction. Populate rating or viewPercentage
     *                       accordingly.
     * @return The added interaction as a DTO.
     */
    @Operation(summary = "Add a new interaction for a given user and movie", description = "Adds an interaction for a given user and movie, such as rating or viewing. The interaction type (rating or view) is determined by the data in the DTO.")
    @PostMapping("/{userId}/{movieId}/")
    public InteractionDTO addInteraction(
            @Parameter(description = "The user id for which the interaction is being added", required = true) @PathVariable(required = true) Long userId,

            @Parameter(description = "The movie id for which the interaction is being added", required = true) @PathVariable(required = true) Long movieId,

            @Parameter(description = "The interaction data to be added (rating, view percentage, etc.)", required = true) @Valid @RequestBody(required = true) InteractionInsertDTO interactionDTO) {

        log.info(
                "Received request to add interaction for user: {} on movie: {} with type: {}, rating: {}, view percentage: {}",
                userId, movieId, interactionDTO.getType(), interactionDTO.getRating(),
                interactionDTO.getViewPercentage());
        return interactionService.addInteraction(userId, movieId, interactionDTO);
    }
}
