package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineGetAllDTO;

import java.util.List;

public interface AllowancePolicyLineService {
    void create(AllowancePolicyLineDTO allowancePolicyLineDTOS, Long allowancePolicyId);
    void update(AllowancePolicyLineDTO allowancePolicyLineDTOS, Long allowancePolicyId);
    List<AllowancePolicyLineGetAllDTO> findByAllowancePolicyId(Long allowancePolicyId);
    void deleteAll(Long allowancePolicyId);

    void deleteById(List<Long> lineIds, Long allowancePolicyId);
}
