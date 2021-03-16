package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.models.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
    Collection<String> getAllDepartureCountriesNames();

    /**
     * Get the Trajectory by departure and arrival country names
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

    /**
     * Remove User from Trajectory entity
     *
     * @param user source data
     */
    void removeUser(User user);

    /**
     * Get list of all users ids in the trajectory
     *
     * @param trajectoryId Trajectory id
     * @return Collection of users ids
     */
    Collection<Integer> getUsersIdsByTrajectoryId(long trajectoryId);

    /**
     * Remove user from trajectory
     *
     * @param user source
     * @param arrivalCountry arrival country
     */
    void removeUserFromTrajectory(User user, String arrivalCountry) throws CountryNotFoundException, IncorrectRequestException;
}
