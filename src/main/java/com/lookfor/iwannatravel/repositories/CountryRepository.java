package com.lookfor.iwannatravel.repositories;

import com.lookfor.iwannatravel.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> getCountryByName(String name);
}
