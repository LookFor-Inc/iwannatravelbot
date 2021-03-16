package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.repositories.CountryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Cacheable("countries")
    public List<Country> fetchAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findCountryByName(String name) {
        return countries.stream()
                .filter(country ->
                        name.equalsIgnoreCase(country.getRu()) ||
                        name.equalsIgnoreCase(country.getUa()) ||
                        name.equalsIgnoreCase(country.getBe()) ||
                        name.equalsIgnoreCase(country.getEn()) ||
                        name.equalsIgnoreCase(country.getEs()) ||
                        name.equalsIgnoreCase(country.getPt()) ||
                        name.equalsIgnoreCase(country.getDe()) ||
                        name.equalsIgnoreCase(country.getFr()) ||
                        name.equalsIgnoreCase(country.getIt()) ||
                        name.equalsIgnoreCase(country.getPl()) ||
                        name.equalsIgnoreCase(country.getJs()) ||
                        name.equalsIgnoreCase(country.getLt()) ||
                        name.equalsIgnoreCase(country.getLv()) ||
                        name.equalsIgnoreCase(country.getCz())
                )
                .findFirst();
    }
}
