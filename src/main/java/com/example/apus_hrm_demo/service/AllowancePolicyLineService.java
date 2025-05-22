package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;

import java.util.List;

public interface AllowancePolicyLineService {
    void createOrUpdate(List<AllowancePolicyLineDTO> allowancePolicyLineDTOS, Long allowancePolicyId);
    List<AllowancePolicyLineDTO> findByAllowancePolicyId(Long allowancePolicyId);
    void deleteAll(Long allowancePolicyId);

    void deleteById(List<Long> lineIds, Long allowancePolicyId);
}
