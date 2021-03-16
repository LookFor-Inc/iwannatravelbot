package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.TrajectoryRepository;
import com.lookfor.iwannatravel.services.CountryService;
import com.lookfor.iwannatravel.services.TrajectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        return trajectoryRepository.findByDepartureCountryEnAndArrivalCountryEn(departureCountry, arrivalCountry);
    }

    @Transactional
    public void saveByUserAndCountries(
            User user,
            Country departureCountry,
            Country arrivalCountry
    ) {
        Optional<Trajectory> trajectoryOptional =
                getTrajectoryByDepartureCountryEnAndArrivalCountryEn(departureCountry.getEn(), arrivalCountry.getEn());

        Trajectory trajectory;
        if (trajectoryOptional.isEmpty()) {
            trajectory = Trajectory.builder()
                    .user(user)
                    .departureCountry(departureCountry)
                    .arrivalCountry(arrivalCountry)
                    .build();
        } else {
            trajectory = trajectoryOptional.get();
            trajectory.getUsers().add(user);
        }
        save(trajectory);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        List<Trajectory> trajectoriesByDepartureCountry =
                trajectoryRepository.findAllByDepartureCountry(user.getCountry());
        trajectoriesByDepartureCountry.forEach(
                trajectory -> trajectory.getUsers().remove(user)
        );
        trajectoryRepository.saveAll(trajectoriesByDepartureCountry);
    }

    @Override
    @Transactional
    public Collection<Integer> getUsersIdsByTrajectoryId(long trajectoryId) {
        Optional<Trajectory> trajectoryOptional = trajectoryRepository.findById(trajectoryId);
        List<Integer> ids = new ArrayList<>();

        if (trajectoryOptional.isPresent()) {
            ids = trajectoryOptional.get()
                    .getUsers().stream()
                    .map(User::getTelegramUserId)
                    .collect(Collectors.toList());
        }
        return ids;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trajectory> getTrajectoriesByDepartureCountryName(String departureCountry) {
        Optional<Country> countryOptional = countryService.findCountryByName(departureCountry);
        List<Trajectory> list = new ArrayList<>();

        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            list = trajectoryRepository.findAllByDepartureCountry(country);
        }

        return list;
    }

    @Override
    @Transactional
    public void removeUserFromTrajectory(
            User user,
            String arrivalCountry
    ) throws CountryNotFoundException, IncorrectRequestException {
        List<Trajectory> trajectoriesByArrivalCountry =
                trajectoryRepository.findAllByArrivalCountryEn(arrivalCountry);
        if (trajectoriesByArrivalCountry.isEmpty()) {
            throw new CountryNotFoundException(arrivalCountry);
        }

        Trajectory userTrajectory =
                trajectoriesByArrivalCountry.stream()
                        .filter(trajectory -> trajectory.getUsers().contains(user))
                        .findFirst()
                        .orElse(null);

        if (userTrajectory == null) {
            throw new IncorrectRequestException(
                    String.format("*%s* is not your favorite country😔", arrivalCountry)
            );
        }
        userTrajectory.getUsers().remove(user);
        trajectoryRepository.save(userTrajectory);
    }
}
