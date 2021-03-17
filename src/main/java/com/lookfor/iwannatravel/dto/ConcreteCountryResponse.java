package com.lookfor.iwannatravel.dto;

import com.lookfor.iwannatravel.models.AllowStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
public class ConcreteCountryResponse {
    @NotNull
    private String countryName;
    @NotNull
    private Date lastUpdate;
    @NotNull
    private AllowStatus citizens;
    @NotNull
    private AllowStatus foreigners;
    @NotNull
    private AllowStatus tourism;
    @NotNull
    private AllowStatus quarantine;
}
