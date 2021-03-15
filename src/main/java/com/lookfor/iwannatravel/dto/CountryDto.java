package com.lookfor.iwannatravel.dto;

import com.lookfor.iwannatravel.models.Country;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountryDto {
    private String name;
    private boolean tourism;
    private String documents;
    private boolean quarantine;
    private int quarantineDays;
    private String quarantineNote;
    private String note;

    public Country toEntity() {
        return Country.builder()
//                .name(name)
//                .tourism(tourism)
//                .documents(documents)
//                .quarantine(quarantine)
//                .quarantineDays(quarantineDays)
//                .quarantineNote(quarantineNote)
//                .note(note)
                .build();
    }
}
