package com.lookfor.iwannatravel.models;

import lombok.Getter;

import javax.persistence.*;

/**
 * Country entity
 */
@Getter
@Entity
@Table(name = "countries")
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
}
