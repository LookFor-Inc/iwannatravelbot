package com.lookfor.iwannatravel.models;

import com.lookfor.iwannatravel.dto.CountryStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Trajectory entity
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trajectories")
public class Trajectory implements Comparable<CountryStatus> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean restricted;

    @Lob
    @Column
    private String note;

    @Singular
    @ManyToMany
    private Set<User> users;

    @ManyToOne
    private Country departureCountry;

    @ManyToOne
    private Country arrivalCountry;

    @Override
    public int compareTo(CountryStatus status) {
        int r = Boolean.compare(restricted, !status.isTravel());
        boolean n = note == null || !note.equalsIgnoreCase(status.getNote());
        return (r != 0 || n) ? 1 : 0;
    }
}
