package com.conentwise.recommendationsystem.recommendationsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.validation.Validator;

import com.conentwise.recommendationsystem.recommendationsystem.businesslogic.services.InteractionService;
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

public class InteractionServiceTest {

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private InteractionRepositoryExtended interactionRepositoryExtended;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private InteractionService interactionService;

    @Spy
    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInteractionViewCurrent() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Interaction lastCurrentInteraction = new Interaction();
        lastCurrentInteraction.setIsCurrent(true);
        lastCurrentInteraction.setViewPercentage(50);
        lastCurrentInteraction.setRatingType(ERatingType.IMPLICIT);
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(lastCurrentInteraction);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(79);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(2)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 4);
        assertEquals(result.getRatingType(), ERatingType.IMPLICIT);
        assertTrue(result.getIsCurrent());
    }

    
    @Test
    void testAddInteractionViewNotCurrent() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Interaction lastCurrentInteraction = new Interaction();
        lastCurrentInteraction.setIsCurrent(true);
        lastCurrentInteraction.setViewPercentage(90);
        lastCurrentInteraction.setRatingType(ERatingType.IMPLICIT);
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(lastCurrentInteraction);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(79);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(1)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 4);
        assertEquals(result.getRatingType(), ERatingType.IMPLICIT);
        assertFalse(result.getIsCurrent());
    }

    @Test
    void testAddInteractionViewLastNull() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

       
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(null);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(79);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(1)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 4);
        assertEquals(result.getRatingType(), ERatingType.IMPLICIT);
        assertTrue(result.getIsCurrent());
    }

    @Test
    void testAddInteractionRatingLastImplicit() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Interaction lastCurrentInteraction = new Interaction();
        lastCurrentInteraction.setIsCurrent(true);
        lastCurrentInteraction.setViewPercentage(90);
        lastCurrentInteraction.setRatingType(ERatingType.IMPLICIT);
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(lastCurrentInteraction);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(2);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(2)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 2);
        assertEquals(result.getRatingType(), ERatingType.EXPLICIT);
        assertTrue(result.getIsCurrent());
    }

    @Test
    void testAddInteractionRatingLastExplicit() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Interaction lastCurrentInteraction = new Interaction();
        lastCurrentInteraction.setIsCurrent(true);
        lastCurrentInteraction.setRating(5);
        lastCurrentInteraction.setRatingType(ERatingType.EXPLICIT);
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(lastCurrentInteraction);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(2);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(2)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 2);
        assertEquals(result.getRatingType(), ERatingType.EXPLICIT);
        assertTrue(result.getIsCurrent());
    }

    @Test
    void testAddInteractionRatingLastNull() {

        // Setup
        Long userId = 1L;
        Long movieId = 1L;
        User user = new User();
        Movie movie = new Movie();

        // Setup Mock behaviour

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        when(interactionRepository.save(any(Interaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

       
        when(interactionRepository.findByUserIdAndMovieIdAndIsCurrent(userId, movieId,
                true)).thenReturn(null);

        // Setup InteractionInsertDTO
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(2);

        // Act
        InteractionDTO result = interactionService.addInteraction(userId, movieId, interactionDTO);

        // Assert
        verify(interactionRepository, times(1)).save(any(Interaction.class));
        assertNotNull(result);
        assertEquals(result.getRating(), 2);
        assertEquals(result.getRatingType(), ERatingType.EXPLICIT);
        assertTrue(result.getIsCurrent());
    }

}
