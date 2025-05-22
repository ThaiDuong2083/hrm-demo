package com.example.apus_hrm_demo.util;

import java.util.UUID;

public class TraceIdGenarator {
    private TraceIdGenarator() {}
    public static String getTraceId() {
        return UUID.randomUUID().toString();
    }
}
