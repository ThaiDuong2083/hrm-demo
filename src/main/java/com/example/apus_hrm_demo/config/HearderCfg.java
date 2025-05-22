package com.example.apus_hrm_demo.config;

import org.springframework.http.HttpHeaders;

public class HearderCfg {
    private HearderCfg() {}
    public static HttpHeaders getHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        String jwtToken = "Bearer eyJraWQiOiJkZWY3ZDIxZi1lMTY5LTRiYzEtODYyMi05NDY0OTIxZWU1ZTAiLCJhbGciOiJSUzI1NiJ9.eyJ0ZW5hbnRfaWQiOiIyMDEiLCJzdWIiOiJhcG9kaW9AZ21haWwuY29tIiwiaXNfb3duZXIiOnRydWUsImlzcyI6Imh0dHA6Ly8xMC4wLjEuOTY6ODA4MSIsImF1dGhvcml0aWVzIjpbIk1hbnVmYWN0b3J5IiwiQkxETk0iXSwiYXVkIjoidGVzdCIsIm5iZiI6MTc0NzEwNzI4NywidXNlcl9pZCI6NTgsIm9yZ19pZCI6MSwic2NvcGUiOlsiZ2F0ZXdheSJdLCJleHAiOjE3NDc5NzEyODcsImlhdCI6MTc0NzEwNzI4NywianRpIjoiYTE0ZDk5ZjMtZDE4MC00NzI3LTg5NDQtOWQ3ZDJhNDgyMTJjIn0.Z13K01YTQMn92enty4z5PQjW7HDBHsHobsaRRv2faCJfk8qRHKah8YITzaJJfUBdRFhEZQFfuZppu6PnyqW0roMS_5ZXiEvmkil-NL17V96lQ-P5jTNJgY2HYmUHT5UDXPn_CM2KhyoicnVyG7Eicdjavk0BPZlMu87r5ry5sbfA6tfxQMgNUoNDr-d-Q7XBQj26bZDuQ64qa0Nblumfq3hlXhMFBipoNaeICpNX3qtMsj9Z_A1Hgn7VWBEUu5tYME0jfbPwbGJyD4n1EThfo_RNpnaV-0gKsql9_sd7_d-hB1OP0LYmCmNJvTk2IjTBBI4WdgQL1egpMfN-i2AJ2A";
        headers.set("Authorization", jwtToken);
        headers.set("X-TenantId", "201");
        return headers;
    }
}
