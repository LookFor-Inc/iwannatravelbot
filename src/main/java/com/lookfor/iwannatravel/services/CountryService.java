package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.Country;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.Country}
 */
public interface CountryService {
    /**
     * Fetch all Country entities
     *
     * @return list of countries
     */
    List<Country> fetchAllCountries();

    /**
     * Get Country by its name
     *
     * @param name of Country
     * @return Country
     */
    Optional<Country> findCountryByName(String name);

    /**
     * Get all countries (sorted by en name)
     *
     * @return list of countries
     */
    List<Country> getAllSortedCountries();
}
