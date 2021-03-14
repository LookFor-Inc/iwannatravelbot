package com.lookfor.iwannatravel.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tracked_countries")
public class TrackedCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer userId;

    @Column
    private Boolean restricted;

    @OneToMany(mappedBy = "trackedCountry")
    private Set<Country> countryFrom;

    @OneToMany(mappedBy = "trackedCountry")
    private Set<Country> countryTo;

    @OneToMany(mappedBy = "trackedCountry")
    private Set<User> users;
}
