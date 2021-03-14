package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.bot.Command;
import com.lookfor.iwannatravel.exceptions.CommandNotFoundException;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.bot.Command}
 */
public interface CommandService {
    /**
     * Find a command name from message
     *
     * @param message received text message
     * @return command to manage
     * @throws CommandNotFoundException incorrect (not found) command exp
     */
    Command findCommandInMessage(String message) throws CommandNotFoundException;
}
