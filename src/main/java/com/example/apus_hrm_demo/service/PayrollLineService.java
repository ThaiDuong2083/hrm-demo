package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;

import java.util.List;

public interface PayrollLineService {
    void create(PayrollLineDTO line, Long payrollId);
    void update(PayrollLineDTO line, Long payrollId);
    void deleteAll(Long payrollId);
    List<PayrollLineGetAllDTO> findAll(Long payrollId);
}
