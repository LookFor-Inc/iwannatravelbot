package com.lookfor.iwannatravel.models;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

/**
 * Country entity
 */
@Getter
@Entity
@Builder
@Table(name = "countries")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ru;
    private String ua;
    private String be;
    private String en;
    private String es;
    private String pt;
    private String de;
    private String fr;
    private String it;
    private String pl;
    private String js;
    private String lt;
    private String lv;
    private String cz;

    @OneToMany(mappedBy = "country")
    private Set<User> users;

    @OneToMany(mappedBy = "country")
    private Set<Trajectory> trajectories;
}
