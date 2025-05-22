package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.base.BaseDTO;

import java.util.List;
import java.util.Set;

public interface ExtenalService {
    List<BaseDTO> getCurrency(Set<Long> currencyIds);
    List<BaseDTO> getUom(Set<Long> uomIds);
    List<BaseDTO> getEmployees(Set<Long> employeeIds);
    List<BaseDTO> getPositions(Set<Long> positionIds);
    List<BaseDTO> getDepartments(Set<Long> departmentIds);
}
