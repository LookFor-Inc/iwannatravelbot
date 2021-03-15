package com.lookfor.iwannatravel.exceptions;

/**
 * Country not found exception
 */
public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String countryName) {
        super(String.format("Country '%s' not found", countryName));
    }
}
