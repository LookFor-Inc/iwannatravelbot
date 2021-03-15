package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Trajectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrajectoryRepository extends JpaRepository<Trajectory, Long> {
    @Query(value = "SELECT c.en FROM Trajectory t, Country c WHERE t.departureCountry = c")
    List<String> findAllArrivalCountriesNames();

    @Query(value = "SELECT t FROM Trajectory t " +
            "JOIN t.arrivalCountry c1 " +
            "JOIN t.departureCountry c2 " +
            "WHERE c1.en = ?1 AND c2.en = ?2")
    Optional<Trajectory> findByCountriesNames(String departureCountry, String arrivalCountry);
}
