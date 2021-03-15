package com.lookfor.iwannatravel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CountryStatus {
    @NotNull
    private String countryName;
    @NotNull
    private boolean travel;
    @NotNull
    private String note;
}
