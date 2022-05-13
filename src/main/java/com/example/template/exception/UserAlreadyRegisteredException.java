package com.example.template.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
