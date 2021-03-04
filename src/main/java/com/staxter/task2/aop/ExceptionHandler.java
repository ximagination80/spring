package com.staxter.task2.aop;

import com.google.common.base.CaseFormat;
import com.staxter.task2.dto.ErrorMessageDTO;
import com.staxter.task2.exception.BaseSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@Slf4j
@ControllerAdvice
class ExceptionControllerHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception e) {
        HttpStatus httpCode;
        String code;
        if (e instanceof BaseSystemException) {
            httpCode = ((BaseSystemException) e).getStatus();
            code = ((BaseSystemException) e).getCode();
        } else if (e instanceof MethodArgumentNotValidException) {
            httpCode = HttpStatus.BAD_REQUEST;
            code = classNameToCode(e);
        } else {
            httpCode = HttpStatus.INTERNAL_SERVER_ERROR;
            code = classNameToCode(e);
        }
        return ResponseEntity.status(httpCode).body(new ErrorMessageDTO(code, e.getMessage()));
    }

    private String classNameToCode(Exception e) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, e.getClass().getSimpleName());
    }
}

