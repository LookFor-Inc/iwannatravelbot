package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.bot.Command;

/**
 * Service interface for managing {@link com.lookfor.iwannatravel.bot.Command}
 */
public interface CommandService {
    /**
     * Find a command name from message
     *
     * @param message received text message
     * @return Command to manage
     */
    Command findCommandInMessage(String message);
}
