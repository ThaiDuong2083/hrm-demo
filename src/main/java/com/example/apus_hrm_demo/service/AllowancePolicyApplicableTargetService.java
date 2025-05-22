package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;

import java.util.List;

public interface AllowancePolicyApplicableTargetService {
    void createOrUpdate(List<AllowancePolicyApplicableTargetDTO> allowancePolicyApplicableTargetDTOS, Long allowancePolicyId);
    List<AllowancePolicyApplicableTargetDTO> findByAllowancePolicyId(Long allowancePolicyId, ApplicableType applicableType);
    void delete(Long allowanceId);
}
