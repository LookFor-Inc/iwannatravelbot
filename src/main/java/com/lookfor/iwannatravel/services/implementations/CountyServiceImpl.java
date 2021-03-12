package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.dto.CountryDto;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.repositories.CountryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Optional<Country> fetchByName(String name) {
        return countryRepository.getCountryByName(name);
    }

    @Override
    @Transactional
    public void saveOrUpdate(CountryDto countryDto) {
        Optional<Country> fetchedCountryOpt = fetchByName(countryDto.getName());

        if (fetchedCountryOpt.isPresent()) {
            Country fetchedCountry = fetchedCountryOpt.get();

            if (!needUpdate(fetchedCountry, countryDto)) {
                return;
            }

            log.info("Update country '{}' information", countryDto.getName());
        }

        Country newCountry = countryDto.toEntity();
        countryRepository.save(newCountry);
    }

    @Override
    @Transactional
    public void saveOrUpdateAll(List<CountryDto> countryDtoList) {
        countryDtoList.forEach(this::saveOrUpdate);
    }

    private boolean needUpdate(Country country, CountryDto countryDto) {
        return country.compareTo(countryDto) != 0;
    }
}
