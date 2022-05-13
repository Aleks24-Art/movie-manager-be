package com.example.template.controller;

import com.example.template.dto.ErrorDto;
import com.example.template.exception.UserAlreadyRegisteredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllersExceptionsHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorDto> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException exception) {
        final String message = exception.getMessage();
        log.error(message, exception);
        return new HttpEntity<>(new ErrorDto(message));
    }
}
