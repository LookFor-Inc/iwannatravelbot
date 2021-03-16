package com.lookfor.iwannatravel.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "trajectories")
public class Trajectory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean restricted;

    @ManyToMany
    private Set<User> users;

    @ManyToOne
    private Country departureCountry;

    @ManyToOne
    private Country arrivalCountry;
}
