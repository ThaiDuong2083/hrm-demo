package com.example.apus_hrm_demo.filter;

import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollFilter {
    private String code;
    private Long departmentId;
    private Long positionId;
    private Cycle cycle;
}
