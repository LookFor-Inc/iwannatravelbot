package com.lookfor.iwannatravel.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Trajectory entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trajectories")
public class Trajectory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean restricted;

    @Singular
    @ManyToMany
    private Set<User> users;

    @ManyToOne
    private Country departureCountry;

    @ManyToOne
    private Country arrivalCountry;
}
