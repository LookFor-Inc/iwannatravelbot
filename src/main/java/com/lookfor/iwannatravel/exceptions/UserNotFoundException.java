package com.lookfor.iwannatravel.exceptions;

/**
 * User not found exception
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer userId) {
        super(String.format("User with id='%s' not found", userId));
    }
}
