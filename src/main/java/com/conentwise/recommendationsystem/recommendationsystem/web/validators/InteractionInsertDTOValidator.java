package com.conentwise.recommendationsystem.recommendationsystem.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.web.dtos.InteractionInsertDTO;

public class InteractionInsertDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return InteractionInsertDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InteractionInsertDTO interactionDTO = (InteractionInsertDTO) target;

        if (interactionDTO.getType() == null) {
            errors.rejectValue("type", "interaction.type.null", "Interaction type must be provided.");
            return;
        }

        if (interactionDTO.getType().equals(EInteractionType.RATING) && interactionDTO.getRating() == null) {
            errors.rejectValue("rating", "interaction.rating.required", "Rating must be provided for RATING type.");
            return;
        }

        if (interactionDTO.getType().equals(EInteractionType.RATING)
                && (interactionDTO.getRating() < 1 || interactionDTO.getRating() > 5)) {
            errors.rejectValue("rating", "interaction.rating.outOfRange",
                    "Rating must be between 1 and 5 for RATING type.");
        }

        if (interactionDTO.getType().equals(EInteractionType.VIEW) && interactionDTO.getViewPercentage() == null) {
            errors.rejectValue("viewPercentage", "interaction.viewPercentage.required",
                    "View percentage must be provided for VIEW type.");
            return;
        }

        if (interactionDTO.getType().equals(EInteractionType.VIEW)
                && (interactionDTO.getViewPercentage() < 0 || interactionDTO.getViewPercentage() > 100)) {
            errors.rejectValue("viewPercentage", "interaction.viewPercentage.outOfRange",
                    "View percentage must be between 0 and 100 for VIEW type.");
        }

    }
}
