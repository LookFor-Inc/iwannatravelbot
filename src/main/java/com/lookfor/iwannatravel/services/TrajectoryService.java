package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.models.User;

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
     * Save new Trajectory entity by user and his countries
     *
     * @param user User entity
     * @param departureCountry departure Country
     * @param arrivalCountry arrival Country
     */
    void saveByUserAndCountries(User user, Country departureCountry, Country arrivalCountry);

    /**
     * Get list of all names of the departures countries
     *
     * @return list of country names
     */
    Collection<String> getAllArrivalCountriesNames();

    /**
     * Get the Trajectory by departure and arrival country names
     *
     * @param departureCountry String
     * @param arrivalCountry String
     * @return Trajectory object
     */
    Optional<Trajectory> getTrajectoryByCountriesNames(String departureCountry, String arrivalCountry);

    /**
     * Remove User from Trajectory entity
     *
     * @param user source data
     */
    void removeUser(User user);
}
