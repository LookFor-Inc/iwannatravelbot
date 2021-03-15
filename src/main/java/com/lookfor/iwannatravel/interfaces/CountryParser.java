package com.lookfor.iwannatravel.interfaces;

import com.lookfor.iwannatravel.dto.ConcreteCountryResponse;
import com.lookfor.iwannatravel.dto.CountryRequest;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

@Async
public interface CountryParser {
    /**
     * Find all information about one country
     *
     * @return ConcreteCountryResponse object
     */
    Future<ConcreteCountryResponse> getResult(CountryRequest request);
}
