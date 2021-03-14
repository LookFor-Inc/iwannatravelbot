package com.lookfor.iwannatravel.models;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne
    private TrackedCountry trackedCountry;

    @ManyToOne
    private Country country;
}
