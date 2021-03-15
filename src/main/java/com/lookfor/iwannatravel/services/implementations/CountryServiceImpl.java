package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.repositories.CountryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private List<Country> countries;

    @PostConstruct
    private void loadCountries() {
        countries = fetchAllCountries();
    }

    @Cacheable("countries")
    @Override
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryByName(String name) throws CountryNotFoundException {
        return countries.stream()
                .filter(country ->
                        name.equals(country.getRu().toLowerCase()) ||
                        name.equals(country.getUa().toLowerCase()) ||
                        name.equals(country.getBe().toLowerCase()) ||
                        name.equals(country.getEn().toLowerCase()) ||
                        name.equals(country.getEs().toLowerCase()) ||
                        name.equals(country.getPt().toLowerCase()) ||
                        name.equals(country.getDe().toLowerCase()) ||
                        name.equals(country.getFr().toLowerCase()) ||
                        name.equals(country.getIt().toLowerCase()) ||
                        name.equals(country.getPl().toLowerCase()) ||
                        name.equals(country.getJs().toLowerCase()) ||
                        name.equals(country.getLt().toLowerCase()) ||
                        name.equals(country.getLv().toLowerCase()) ||
                        name.equals(country.getCz().toLowerCase())
                )
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException(name));
    }
}
