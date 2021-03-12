package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.dto.CountryDto;
import com.lookfor.iwannatravel.models.Country;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.Country}
 */
public interface CountryService {
    /**
     * Fetch a Country by name
     *
     * @param name country name
     * @return country
     */
    Optional<Country> fetchByName(String name);

    /**
     * Save or update a Country
     *
     * @param countryDto country dto object
     */
    void saveOrUpdate(CountryDto countryDto);

    /**
     * Save or update the list of Countries
     *
     * @param countryDtoList list of country dto objects
     */
    void saveOrUpdateAll(List<CountryDto> countryDtoList);
}
