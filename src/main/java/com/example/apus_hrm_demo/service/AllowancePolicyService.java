package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import org.springframework.data.domain.Pageable;

public interface AllowancePolicyService {
    BaseResponse<ResponseAfterCUDTO> create(AllowancePolicyDTO allowancePolicyDTO);
    BaseResponse<ResponseAfterCUDTO> update( AllowancePolicyDTO allowancePolicyDTO);
    BaseResponse<AllowancePolicyDTO> findById(Long allowanceId);
    BaseResponse<ResponsePage<AllowancePolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter);
    void delete(Long allowanceId);

}
