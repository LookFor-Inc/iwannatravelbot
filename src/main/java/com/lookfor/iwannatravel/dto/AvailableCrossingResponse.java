package com.lookfor.iwannatravel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvailableCrossingResponse {
    @NotNull
    private String fromCountry;
    @NotNull
    private List<CountryStatus> countryStatusList;
}
