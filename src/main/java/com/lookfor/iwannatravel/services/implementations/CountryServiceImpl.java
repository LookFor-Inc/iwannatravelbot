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
        final String target = name.toLowerCase();
        return countries.stream()
                .filter(country -> target.equalsIgnoreCase(country.getRu()) ||
                        target.equalsIgnoreCase(country.getUa()) ||
                        target.equalsIgnoreCase(country.getBe()) ||
                        target.equalsIgnoreCase(country.getEn()) ||
                        target.equalsIgnoreCase(country.getEs()) ||
                        target.equalsIgnoreCase(country.getPt()) ||
                        target.equalsIgnoreCase(country.getDe()) ||
                        target.equalsIgnoreCase(country.getFr()) ||
                        target.equalsIgnoreCase(country.getIt()) ||
                        target.equalsIgnoreCase(country.getPl()) ||
                        target.equalsIgnoreCase(country.getJs()) ||
                        target.equalsIgnoreCase(country.getLt()) ||
                        target.equalsIgnoreCase(country.getLv()) ||
                        target.equalsIgnoreCase(country.getCz())
                )
                .findFirst();
    }
}
