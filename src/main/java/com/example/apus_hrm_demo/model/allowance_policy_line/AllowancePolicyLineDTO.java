package com.example.apus_hrm_demo.model.allowance_policy_line;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowancePolicyLineDTO {
    private Long id;
    private BaseDTO allowance;
    private Cycle cycle;
    private Double amount;
}

