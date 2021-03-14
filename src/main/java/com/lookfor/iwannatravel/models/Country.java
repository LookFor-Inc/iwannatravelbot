package com.lookfor.iwannatravel.models;

import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.Set;

/**
 * Country entity
 */
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Unique
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<User> users;

    @ManyToOne
    private TrackedCountry trackedCountry;
}
