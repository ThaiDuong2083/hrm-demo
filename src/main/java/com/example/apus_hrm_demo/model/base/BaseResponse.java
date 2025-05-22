package com.example.apus_hrm_demo.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse <T> {
    private String traceId;
    private String message;
    private T data;
}
