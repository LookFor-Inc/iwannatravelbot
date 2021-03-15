package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.models.Country;

import java.util.List;

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
    Country getCountryByName(String name) throws CountryNotFoundException;
}
