package com.lookfor.iwannatravel.exceptions;

/**
 * Command not found custom exception
 */
public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String commandName) {
        super(String.format("Command '%s' not found", commandName));
    }
}
