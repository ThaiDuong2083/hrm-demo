package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;

import java.util.List;

public interface AllowancePolicyApplicableTargetService {
    void create(AllowancePolicyApplicableTargetDetailDTO allowancePolicyApplicableTargetDetailDTO, Long allowancePolicyId);
    void update( AllowancePolicyApplicableTargetDetailDTO allowancePolicyApplicableTargetDetailDTO, Long allowancePolicyId);
    List<AllowancePolicyApplicableTargetDetailDTO> findByAllowancePolicyId(Long allowancePolicyId, ApplicableType applicableType);
    void delete(Long allowanceId);
}
