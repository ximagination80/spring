package com.staxter.task2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessageDTO {
    private final String code;
    private final String message;
}
