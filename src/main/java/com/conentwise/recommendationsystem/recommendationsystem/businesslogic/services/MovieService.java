package com.conentwise.recommendationsystem.recommendationsystem.businesslogic.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.Movie;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.genre.GenreRepositoryExtended;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.movie.MovieRepositoryExtended;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.repositories.user.UserRepository;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.MovieDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieService {

    @Autowired
    private MovieRepositoryExtended movieRepositoryExtended;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepositoryExtended genreRepositoryExtended;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Fetches all movies with optional filters of genre, avg min rating and avg max rating.
     * If no filters are provided, all movies are returned.
     * 
     * @param genres   list of genres to filter movies by
     * @param minRating avg minimum rating value to filter movies by
     * @param maxRating avg maximum rating value to filter movies by
     * @return List of movies with the given filters
     */
    public List<MovieDTO> getAllMovies(List<String> genres, Integer minRating, Integer maxRating) {

        log.info("Fetching all movies with optional filters - genre: {}, minRating: {}, maxRating: {}", genres,
                minRating, maxRating);

        List<MovieDTO> result = movieRepositoryExtended
                .findAllMovies(genres, minRating, maxRating)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * Provides movie recommendations for a specific user based on their preferences.
     *
     * @param userId the ID of the user for whom movie recommendations are requested
     * @return a list of recommended movies tailored to the user's preferences
     */
    public List<MovieDTO> getAllRecommendMoviesForUser(Long userId) {

        // validation
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        log.info("Received request to recommend movies for user: {}", userId);

        List<String> preferredGenres = genreRepositoryExtended.findPreferredGenresForUser(userId);

        log.debug("Fetching recommended movies for user: {} with preferred genres: {}", userId, preferredGenres);
        List<Movie> recommendedMovies = movieRepositoryExtended.findAllRecommendMoviesForUser(userId, preferredGenres);

        List<MovieDTO> result = recommendedMovies.stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());

        return result;
    }
}