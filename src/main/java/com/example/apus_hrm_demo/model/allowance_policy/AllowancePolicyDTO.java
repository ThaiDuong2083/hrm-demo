package com.example.apus_hrm_demo.model.allowance_policy;

import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AllowancePolicyDTO {
    private Long id;
    private String name;
    private String code;
    private PolicyType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private ApplicableType applicableType;
    private List<AllowancePolicyApplicableTargetDetailDTO> target;
    private List<AllowancePolicyLineDTO> allowancePolicyLine;
    private String description;
    private State state;
}
