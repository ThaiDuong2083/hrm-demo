package com.example.apus_hrm_demo.model.payroll;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupForPayrollDTO;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import com.example.apus_hrm_demo.util.enum_util.PayrollType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PayrollDTO {
    private Long id;
    private BaseDTO employee;
    private BaseDTO department;
    private BaseDTO position;
    private Cycle cycle;
    private PayrollType type;
    private Double totalAllowanceAmount;
    private LocalDate startDate;
    private String note;
    private GroupForPayrollDTO groupAllowances;
    private GroupForPayrollDTO groupRewards;
}
