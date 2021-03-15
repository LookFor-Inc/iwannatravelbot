package com.lookfor.iwannatravel.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * User entity
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Integer telegramUserId;

    private String username;

    @ManyToMany
    private Set<Trajectory> trajectories;

    @ManyToOne
    private Country country;
}
