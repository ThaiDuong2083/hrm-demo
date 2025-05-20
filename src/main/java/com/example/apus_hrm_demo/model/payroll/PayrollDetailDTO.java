package com.example.apus_hrm_demo.model.payroll;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupAllowanceForPayrollDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupRewardForPayrollDTO;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PayrollDetailDTO {
    private Long id;
    private BaseDTO employee;
    private BaseDTO department;
    private BaseDTO position;
    private Cycle cycle;
    private Double totalAllowanceAmount;
    private LocalDate startDate;
    private String note;
    private List<GroupAllowanceForPayrollDTO> groupAllowances;
    private List<GroupRewardForPayrollDTO> groupRewards;
}
