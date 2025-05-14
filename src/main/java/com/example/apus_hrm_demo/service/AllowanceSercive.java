package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import org.springframework.data.domain.Pageable;

public interface AllowanceSercive {
    BaseResponse<ResponseAfterCUDTO> createAllowance(AllowanceDTO allowanceFormDTO);
    BaseResponse<ResponseAfterCUDTO> updateAllowance(AllowanceDTO allowanceFormDTO);

    BaseResponse<ResponsePage<AllowanceGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable);
    void deleteAllowance(Long id);
    BaseResponse<AllowanceDTO> findById(Long id);
}
