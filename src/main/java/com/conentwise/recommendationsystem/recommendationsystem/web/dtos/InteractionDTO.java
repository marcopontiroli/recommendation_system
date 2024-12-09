package com.conentwise.recommendationsystem.recommendationsystem.web.dtos;

import java.time.LocalDateTime;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.ERatingType;

import lombok.Data;

@Data
public class InteractionDTO {

    private Long id;

    private LocalDateTime date;

    private EInteractionType type;

    private Integer rating;

    private ERatingType ratingType;

    private Integer viewPercentage;

    private Boolean isCurrent;

    private Long movieId;

    private Long userId;
}
