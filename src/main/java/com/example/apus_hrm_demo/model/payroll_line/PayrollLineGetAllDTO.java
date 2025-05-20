package com.example.apus_hrm_demo.model.payroll_line;

import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollLineGetAllDTO {
    private String targetName;
    private Double amount;
    private Double taxableAmount;
    private Double insuranceAmount;
    private Cycle cycle;
}
