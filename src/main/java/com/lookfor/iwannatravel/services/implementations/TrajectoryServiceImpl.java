package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.repositories.TrajectoryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import com.lookfor.iwannatravel.services.TrajectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrajectoryServiceImpl implements TrajectoryService {
    private final TrajectoryRepository trajectoryRepository;
    private final CountryService countryService;

    @Override
    public void save(Trajectory trajectory) {
        trajectoryRepository.save(trajectory);
    }

    @Override
    public Collection<String> getAllDepartureCountriesNames() {
        return new HashSet<>(trajectoryRepository.findAllDepartureCountriesNames());
    }

    @Override
    public Optional<Trajectory> getTrajectoryByDepartureCountryEnAndArrivalCountryEn(String departureCountry, String arrivalCountry) {
        return trajectoryRepository.findByDepartureCountryEnAndArrivalCountryEn(
                StringUtils.capitalize(departureCountry.toLowerCase()),
                StringUtils.capitalize(arrivalCountry.toLowerCase())
        );
    }

    @Override
    public List<Trajectory> getTrajectoriesByDepartureCountryName(String departureCountry) {
        Optional<Country> countryOptional = countryService.findCountryByName(departureCountry);
        List<Trajectory> list = new ArrayList<>();

        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            list = trajectoryRepository.findAllByDepartureCountry(country);
        }

        return list;
    }
}
