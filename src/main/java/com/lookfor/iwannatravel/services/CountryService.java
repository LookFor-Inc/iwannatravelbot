package com.lookfor.iwannatravel.services;

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
}
