package com.staxter.task2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseSystemException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    public BaseSystemException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
