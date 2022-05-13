package com.movie.manager.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
