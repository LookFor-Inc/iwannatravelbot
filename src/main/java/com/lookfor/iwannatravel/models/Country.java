package com.lookfor.iwannatravel.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Country entity
 */
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean tourism;

    @Column
    private String documents;

    @Column
    private Boolean quarantine;

    @Column
    private Integer quarantineDays;

    @Column
    private String quarantineNote;

    @Column
    private String note;

    @Column
    @ManyToMany(mappedBy = "countries")
    private Set<User> users;
}
