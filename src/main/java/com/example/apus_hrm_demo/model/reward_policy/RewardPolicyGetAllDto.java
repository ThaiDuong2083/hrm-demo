package com.example.apus_hrm_demo.model.reward_policy;

import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RewardPolicyGetAllDto {
    private Long id;
    private String name;
    private String code;
    private PolicyType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private State state;
}
