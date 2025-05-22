package com.example.apus_hrm_demo.model.reward_policy_line;

import com.example.apus_hrm_demo.model.reward.RewardForPolicyLineDTO;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardPolicyLineDTO {
    private Long id;
    private RewardForPolicyLineDTO reward;
    private Cycle cycle;
    private Double amount;
}

