package com.example.apus_hrm_demo.model.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse <T> {
    private String traceId;
    private String message;
    private T data;
}
