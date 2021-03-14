package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.TrackedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackedCountryRepository extends JpaRepository<TrackedCountry, Long> {
    List<Integer> getCountriesNameToByUserId(Integer id);
}
