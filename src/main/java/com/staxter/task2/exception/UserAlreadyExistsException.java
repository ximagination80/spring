package com.staxter.task2.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseSystemException {

    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "USER_ALREADY_EXISTS",
                "A user with the given username already exists");
    }
}
