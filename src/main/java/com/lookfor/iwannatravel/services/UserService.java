package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.User;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.models.User}
 */
public interface UserService {
    /**
     * Fetch a User by his telegram id
     *
     * @param telegramUserId User's telegram id
     * @return persisted entity
     */
    User fetchByTelegramUserId(Integer telegramUserId);

    /**
     * Save a User
     *
     * @param user entity to save
     */
    void save(User user);
}
