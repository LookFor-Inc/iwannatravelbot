package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.repositories.TrajectoryRepository;
import com.lookfor.iwannatravel.services.TrajectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrajectoryServiceImpl implements TrajectoryService {
    private final TrajectoryRepository trajectoryRepository;

    @Override
    public Collection<String> getAllArrivalCountriesNames() {
        return new HashSet<>(trajectoryRepository.findAllArrivalCountriesNames());
    }

    @Override
    public Optional<Trajectory> getTrajectoryByCountriesNames(String departureCountry, String arrivalCountry) {
        return trajectoryRepository.findByCountriesNames(
                StringUtils.capitalize(departureCountry),
                StringUtils.capitalize(arrivalCountry)
        );
    }
}
