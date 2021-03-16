package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.exceptions.CountryNotFoundException;
import com.lookfor.iwannatravel.exceptions.IncorrectRequestException;
import com.lookfor.iwannatravel.exceptions.UserNotFoundException;
import com.lookfor.iwannatravel.models.Trajectory;
import com.lookfor.iwannatravel.models.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.User}
 */
public interface UserService {
    /**
     * Save a User
     *
     * @param user entity to save
     */
    void save(User user);

    /**
     * Fetch a User by his telegram id
     *
     * @param telegramUserId User's telegram id
     * @return persisted entity
     */
    Optional<User> findByTelegramUserId(Integer telegramUserId);

    /**
     * Save User's updates or create a new one
     *
     * @param message received Message
     */
    void saveUpdates(Message message);

    /**
     * Save user's departure country
     *
     * @param userId id of a user
     * @param countryName user's Country name
     */
    void saveUserDepartureCountry(Integer userId, String countryName) throws CountryNotFoundException, UserNotFoundException;

    /**
     * Save user's arrival country
     *
     * @param userId id of a user
     * @param countryName user's Country name
     */
    void saveUserArrivalCountry(Integer userId, String countryName) throws CountryNotFoundException, UserNotFoundException, IncorrectRequestException;

    /**
     * Fetch list of arrival country names (in english)
     *
     * @param userId id of a user
     * @return list of user's arrival country names (strings)
     * @throws UserNotFoundException exp
     */
    List<String> fetchUserArrivalCountries(Integer userId) throws UserNotFoundException;

    /**
     * Get name of user's departure country name
     *
     * @param userId id of a user
     * @return name of a country (in english)
     * @throws UserNotFoundException exp
     */
    String getUserDepartureCountryName(Integer userId) throws UserNotFoundException, IncorrectRequestException;
}
