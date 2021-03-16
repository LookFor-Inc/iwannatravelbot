package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TrajectoryRepository extends JpaRepository<Trajectory, Long> {
    @Query(value = "SELECT c.en FROM Trajectory t, Country c WHERE t.departureCountry = c")
    List<String> findAllDepartureCountriesNames();

    @Query(value = "SELECT t FROM Trajectory t " +
            "JOIN t.departureCountry c1 " +
            "JOIN t.arrivalCountry c2 " +
            "WHERE LOWER(c1.en) = LOWER(?1) AND LOWER(c2.en) = LOWER(?2)")
    Optional<Trajectory> findByDepartureCountryEnAndArrivalCountryEn(String departureCountry, String arrivalCountry);

    List<Trajectory> findAllByDepartureCountry(Country departureCountry);
}
