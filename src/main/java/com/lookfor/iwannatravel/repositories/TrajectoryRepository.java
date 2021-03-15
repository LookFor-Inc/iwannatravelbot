package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Trajectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface TrajectoryRepository extends JpaRepository<Trajectory, Long> {
    @Query(value = "SELECT c.en FROM Trajectory t, Country c WHERE t.departureCountry = c")
    List<String> getAllArrivalCountriesNames();
}
