package com.lookfor.iwannatravel.exceptions;

/**
 * Incorrect country exception
 */
public class IncorrectRequestException extends RuntimeException {
    public IncorrectRequestException(String message) {
        super(message);
    }
}
