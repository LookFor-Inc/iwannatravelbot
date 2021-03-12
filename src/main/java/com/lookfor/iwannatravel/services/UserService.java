package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.models.User;
import org.telegram.telegrambots.meta.api.objects.Message;

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

    /**
     * Save User's updates or create a new one
     *
     * @param message received Message
     */
    void saveUpdates(Message message);
}
