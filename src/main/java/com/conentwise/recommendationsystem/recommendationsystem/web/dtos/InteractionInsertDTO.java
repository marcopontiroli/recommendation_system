package com.conentwise.recommendationsystem.recommendationsystem.web.dtos;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;

import lombok.Data;

@Data
public class InteractionInsertDTO {

    private EInteractionType type;

    private Integer rating;

    private Integer viewPercentage;
}
