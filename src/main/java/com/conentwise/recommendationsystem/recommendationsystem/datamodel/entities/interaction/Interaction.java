package com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.interaction;

import java.time.LocalDateTime;

import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.movie.Movie;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.entities.user.User;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.EInteractionType;
import com.conentwise.recommendationsystem.recommendationsystem.datamodel.enums.ERatingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EInteractionType type;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERatingType ratingType;

    private Integer viewPercentage;

    private Boolean isCurrent;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private User user;
}