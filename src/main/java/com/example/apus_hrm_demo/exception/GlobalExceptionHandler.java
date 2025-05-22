package com.example.apus_hrm_demo.exception;

import com.example.apus_hrm_demo.model.base.BaseResponse;

public interface GlobalExceptionHandler {
    BaseResponse<String> handleNullEnityException(NullEntityException ex);
}
