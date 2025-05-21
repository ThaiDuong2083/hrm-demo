package com.example.apus_hrm_demo.model.payroll_line.group;

import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupAllowanceForPayrollDTO {
    private List<String> allGroupName;
    private List<PayrollLineDetailDTO> allowanceLines;
}
