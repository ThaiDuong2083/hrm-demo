package com.example.apus_hrm_demo.filter;

import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PolicyFliter {
    private String name;
    private PolicyType type;
    private ApplicableType applicableType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private State state;
}
