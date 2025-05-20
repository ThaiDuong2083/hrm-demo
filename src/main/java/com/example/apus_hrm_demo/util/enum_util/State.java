package com.example.apus_hrm_demo.util.enum_util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State {
    //dùng chung
    DRAFT, //dự thảo
    VALID,
    INVALID,
    CANCELLED,
    PUBLISH,
    STORE,
}
