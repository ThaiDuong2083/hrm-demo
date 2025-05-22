package com.example.apus_hrm_demo.model.payroll_line.group;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupForPayrollDTO {
    private List<BaseDTO> allGroup;
    private List<PayrollLineDTO> lines;
}
