package com.example.apus_hrm_demo.model.reward_policy;

import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.enum_util.PolicyType;
import com.example.apus_hrm_demo.util.enum_util.State;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RewardPolicyDTO {
    private Long id;
    private String name;
    private String code;
    private PolicyType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ApplicableType applicableType;
    private List<RewardPolicyApplicableTargetDetailDTO> target;
    private List<RewardPolicyLineDTO> rewardPolicyLine;
    private String description;
    private State state;
}
