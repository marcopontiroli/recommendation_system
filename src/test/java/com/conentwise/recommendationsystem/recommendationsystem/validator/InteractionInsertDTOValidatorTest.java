package com.conentwise.recommendationsystem.recommendationsystem.validator;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionInsertDTO;
import com.conentwise.recommendationsystem.recommendationsystem.web.validators.InteractionInsertDTOValidator;

public class InteractionInsertDTOValidatorTest {

    @Mock
    private Errors errors;

    private InteractionInsertDTOValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new InteractionInsertDTOValidator();
    }

    @Test
    void testValidRating() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(4);

        validator.validate(interactionDTO, errors);

        verify(errors, times(0)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void testRatingNull() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(null);

        validator.validate(interactionDTO, errors);

        verify(errors, times(1)).rejectValue("rating", "interaction.rating.required",
                "Rating must be provided for RATING type.");
    }

    @Test
    void testRatingOutOfRange() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.RATING);
        interactionDTO.setRating(6);

        validator.validate(interactionDTO, errors);

        verify(errors, times(1)).rejectValue("rating", "interaction.rating.outOfRange",
                "Rating must be between 1 and 5 for RATING type.");
    }

    @Test
    void testValidView() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(50);

        validator.validate(interactionDTO, errors);

        verify(errors, times(0)).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void testViewPercentageNull() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(null);

        validator.validate(interactionDTO, errors);

        verify(errors, times(1)).rejectValue("viewPercentage", "interaction.viewPercentage.required",
                "View percentage must be provided for VIEW type.");
    }

    @Test
    void testViewPercentageOutOfRange() {
        InteractionInsertDTO interactionDTO = new InteractionInsertDTO();
        interactionDTO.setType(EInteractionType.VIEW);
        interactionDTO.setViewPercentage(150);

        validator.validate(interactionDTO, errors);

        verify(errors, times(1)).rejectValue("viewPercentage", "interaction.viewPercentage.outOfRange",
                "View percentage must be between 0 and 100 for VIEW type.");
    }
}
