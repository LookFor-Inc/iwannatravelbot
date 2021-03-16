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
                .filter(country -> target.equals(country.getRu().toLowerCase()) ||
                        target.equals(country.getUa().toLowerCase()) ||
                        target.equals(country.getBe().toLowerCase()) ||
                        target.equals(country.getEn().toLowerCase()) ||
                        target.equals(country.getEs().toLowerCase()) ||
                        target.equals(country.getPt().toLowerCase()) ||
                        target.equals(country.getDe().toLowerCase()) ||
                        target.equals(country.getFr().toLowerCase()) ||
                        target.equals(country.getIt().toLowerCase()) ||
                        target.equals(country.getPl().toLowerCase()) ||
                        target.equals(country.getJs().toLowerCase()) ||
                        target.equals(country.getLt().toLowerCase()) ||
                        target.equals(country.getLv().toLowerCase()) ||
                        target.equals(country.getCz().toLowerCase())
                )
                .findFirst();
    }
}
