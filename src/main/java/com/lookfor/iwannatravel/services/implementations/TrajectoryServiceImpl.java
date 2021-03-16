package com.lookfor.iwannatravel.services.implementations;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.models.User;
import com.lookfor.iwannatravel.repositories.TrajectoryRepository;
import com.lookfor.iwannatravel.services.TrajectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrajectoryServiceImpl implements TrajectoryService {
    private final TrajectoryRepository trajectoryRepository;

    @Override
    public void save(Trajectory trajectory) {
        trajectoryRepository.save(trajectory);
    }

    @Override
    @Transactional
    public void saveByUserAndCountries(
            User user,
            Country departureCountry,
            Country arrivalCountry
    ) {
        Optional<Trajectory> trajectoryOptional =
                getTrajectoryByCountriesNames(departureCountry.getEn(), arrivalCountry.getEn());

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
    public Collection<String> getAllArrivalCountriesNames() {
        return new HashSet<>(trajectoryRepository.findAllArrivalCountriesNames());
    }

    @Override
    public Optional<Trajectory> getTrajectoryByCountriesNames(String departureCountry, String arrivalCountry) {
        return trajectoryRepository.findByCountriesNames(departureCountry, arrivalCountry);
    }

    @Override
    @Transactional
    public void removeUser(User user) {
        List<Trajectory> trajectoriesByDepartureCountry = trajectoryRepository.findAllByDepartureCountry(user.getCountry());
        trajectoriesByDepartureCountry.forEach(
                trajectory -> trajectory.getUsers().remove(user)
        );
        trajectoryRepository.saveAll(trajectoriesByDepartureCountry);
    }
}
