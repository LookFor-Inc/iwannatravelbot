package com.lookfor.iwannatravel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class AvailableCrossingRequest {
    @NotNull
    private String fromCountry;
}
