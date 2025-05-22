package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupForPayrollDTO;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;

import java.util.List;

public interface PayrollLineService {
    void createOrUpdate(GroupForPayrollDTO groupAllowancesDTO, GroupForPayrollDTO groupRewardDTO, Long payrollId);
    void deleteAll(Long payrollId);
    List<PayrollLineGetAllDTO> findAllByPayrollId(Long payrollId);
    GroupForPayrollDTO findByPayrollId(Long payrollId, PayrollLineType type);
}
