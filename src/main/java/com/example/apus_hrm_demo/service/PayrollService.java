package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.filter.PayrollFilter;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import org.springframework.data.domain.Pageable;

public interface PayrollService {
    BaseResponse<ResponseAfterCUDTO> create(PayrollDTO dto);
    BaseResponse<ResponseAfterCUDTO> update(PayrollDTO dto);
    void delete(Long payrollId);
    BaseResponse<ResponsePage<PayrollGetAllDTO>> getAll(PayrollFilter payrollFilter, Pageable pageable);
    BaseResponse<PayrollDTO> findById(Long payrollId);
}
