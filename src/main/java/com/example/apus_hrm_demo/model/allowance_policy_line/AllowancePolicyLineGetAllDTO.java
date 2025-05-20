package com.example.apus_hrm_demo.model.allowance_policy_line;

import com.example.apus_hrm_demo.model.allowance.AllowanceForPolicyLineDTO;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowancePolicyLineGetAllDTO {
    private Long id;
    private AllowanceForPolicyLineDTO allowance;
    private Cycle cycle;
    private Double amount;
}
