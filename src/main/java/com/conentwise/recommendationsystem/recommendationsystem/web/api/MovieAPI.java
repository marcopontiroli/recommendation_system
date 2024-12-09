package com.conentwise.recommendationsystem.recommendationsystem.web.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conentwise.recommendationsystem.recommendationsystem.businesslogic.services.MovieService;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.MovieDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movie")
@Tag(name = "Movie API", description = "Operations related to movies including fetching, filtering, and recommendations")
public class MovieAPI {

    private final MovieService movieService;

    public MovieAPI(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Fetches all movies with optional filters of genre, avg min rating and avg max
     * rating.
     * If no filters are provided, all movies are returned.
     * 
     * @param genres    list of genres to filter movies by
     * @param minRating avg minimum rating value to filter movies by
     * @param maxRating avg maximum rating value to filter movies by
     * @return List of movies with the given filters
     */
    @Operation(summary = "Fetch all movies with optional filters", description = "Fetches all movies with optional filters of genre, avg min rating and avg max rating. "
            + "If no filters are provided, all movies are returned.")
    @GetMapping("/")
    public List<MovieDTO> getAllMovies(
            @Parameter(description = "List of genres to filter movies by", required = false) @RequestParam(required = false) List<String> genres,

            @Parameter(description = "Minimum average rating to filter movies by", required = false) @RequestParam(required = false) Integer minRating,

            @Parameter(description = "Maximum average rating to filter movies by", required = false) @RequestParam(required = false) Integer maxRating) {

        log.info("Received request to fetch movies with optional filters - genre: {}, minRating: {}, maxRating: {}",
                genres, minRating, maxRating);
        return movieService.getAllMovies(genres, minRating, maxRating);
    }

    /**
     * Provides movie recommendations for a specific user based on their
     * preferences.
     *
     * @param userId the ID of the user for whom movie recommendations are requested
     * @return a list of recommended movies tailored to the user's preferences
     */
    @Operation(summary = "Get movie recommendations for a user", description = "Provides movie recommendations for a specific user based on their preferences.")
    @GetMapping("/{userId}/recommendations")
    public List<MovieDTO> getAllRecommendMoviesForUser(
            @Parameter(description = "ID of the user for whom movie recommendations are requested", required = true) @PathVariable(required = true) Long userId) {

        log.info("Received request for movie recommendations for user: {}", userId);
        return movieService.getAllRecommendMoviesForUser(userId);
    }

    /**
     * Searches for movies based on a search term.
     * Matches words in title, or exact genres, case insensitive. If you want to
     * search for multiple
     * terms, separate them by space.
     * 
     * @param searchTerm the term to search for. Multiple terms can be separated by
     *                   space. Genres are an exact match, title is a partial match.
     *                   This param is case insensitive.
     * @return a list of movies matching the search term
     */
    @Operation(summary = "Search for movies", description = "Searches for movies based on a search term. Matches words in title, or exact genres. "
            + "If you want to search for multiple terms, separate them by space. Genres are an exact match, title is a partial match.")
    @GetMapping("/search/{searchTerm}")
    public List<MovieDTO> searchMovies(
            @Parameter(description = "The term to search for. If you want to search for multiple terms, separate them by space. Genres are an exact match, title is a partial match. This param is case insensitive", required = true) @PathVariable(required = true) String searchTerm) {

        log.info("Received search request for term: {}", searchTerm);
        return movieService.searchMovies(searchTerm);
    }
}
