package com.usercrud.exception;

/**
 * Exception thrown when a username already exists in the system.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username already exists: " + username);
    }

    public UsernameAlreadyExistsException(String message, String username) {
        super(message);
    }
}
