package com.lookfor.iwannatravel.interfaces;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

/**
 * Automatic message sender from bot to user
 */
public interface MessageSender {
    /**
     * Send to a single user
     *
     * @param userId user id
     * @param sourceText text to send
     * @throws TelegramApiException exp
     */
    void sendToUser(Integer userId, String sourceText) throws TelegramApiException;

    /**
     * Send to several users
     *
     * @param usersId collection of users' id
     * @param sourceText text to send
     * @throws TelegramApiException exp
     */
    void sendToUsers(Collection<Integer> usersId, String sourceText) throws TelegramApiException;
}
