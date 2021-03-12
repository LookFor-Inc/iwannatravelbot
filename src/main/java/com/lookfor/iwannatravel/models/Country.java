package com.lookfor.iwannatravel.models;

import com.lookfor.iwannatravel.dto.CountryDto;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.Set;

/**
 * Country entity
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
public class Country implements Comparable<CountryDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Unique
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

    @Override
    public int compareTo(CountryDto anotherCountry) {
        return Boolean.compare(tourism, anotherCountry.isTourism());
    }
}
