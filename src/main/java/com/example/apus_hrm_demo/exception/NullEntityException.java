package com.example.apus_hrm_demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NullEntityException extends RuntimeException {
    private final String traceId;
    private final String code;
}