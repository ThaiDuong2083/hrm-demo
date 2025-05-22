package com.example.apus_hrm_demo.exception;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.util.i18n.GenerateMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerImpl implements GlobalExceptionHandler {
    private final GenerateMessage generateMessage;
    private final HttpServletRequest request;

    @ExceptionHandler(NullEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponse<String> handleNullEnityException(NullEntityException ex) {
        return new BaseResponse<>(ex.getTraceId(),generateMessage.getMessage(ex.getCode(),request.getLocale()), null );
    }
}