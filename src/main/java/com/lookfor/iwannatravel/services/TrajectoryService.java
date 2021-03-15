package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.Trajectory;

import java.util.Collection;
import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.Trajectory}
 */
public interface TrajectoryService {
    /**
     * Get list of all names of the departures countries
     *
     * @return list of country names
     */
    Collection<String> getAllArrivalCountriesNames();

    /**
     * Get the trajectory by departure and arrival country names
     *
     * @param departureCountry String
     * @param arrivalCountry String
     * @return Trajectory object
     */
    Optional<Trajectory> getTrajectoryByCountriesNames(String departureCountry, String arrivalCountry);
}
