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
    @Column
    private Integer telegramUserId;

    @Column
    private String username;

    @Column
    @ManyToMany
    private Set<Country> countries;
}
