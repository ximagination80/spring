package com.staxter.task2.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseSystemException {

    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN",
                "Provided token is invalid or expired");
    }
}
