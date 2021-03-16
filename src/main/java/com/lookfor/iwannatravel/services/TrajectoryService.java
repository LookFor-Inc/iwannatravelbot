package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.Trajectory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.Trajectory}
 */
public interface TrajectoryService {
    /**
     * Save Trajectory entity
     *
     * @param trajectory source entity
     */
    void save(Trajectory trajectory);

    /**
     * Get list of all names of the departures countries
     *
     * @return list of country names
     */
    Collection<String> getAllDepartureCountriesNames();

    /**
     * Get the trajectory by departure and arrival country names
     *
     * @param departureCountry String
     * @param arrivalCountry String
     * @return Trajectory object
     */
    Optional<Trajectory> getTrajectoryByDepartureCountryEnAndArrivalCountryEn(String departureCountry, String arrivalCountry);

    /**
     * Get list of all trajectories by departure country name
     *
     * @param departureCountry String
     * @return list of the Trajectory object
     */
    List<Trajectory> getTrajectoriesByDepartureCountryName(String departureCountry);
}
