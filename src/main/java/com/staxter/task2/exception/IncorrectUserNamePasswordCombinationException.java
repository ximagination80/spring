package com.staxter.task2.exception;

import org.springframework.http.HttpStatus;

public class IncorrectUserNamePasswordCombinationException extends BaseSystemException {

    public IncorrectUserNamePasswordCombinationException() {
        super(HttpStatus.UNAUTHORIZED, "INCORRECT_USERNAME_OR_PASSWORD",
                "User name or password was incorrect");
    }
}
