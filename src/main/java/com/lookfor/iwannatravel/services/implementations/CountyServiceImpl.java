package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.repositories.CountryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }
}
