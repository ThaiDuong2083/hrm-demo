package com.example.apus_hrm_demo.mapper.reward_policy_applicable_target;

import com.example.apus_hrm_demo.repository.RewardPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertInRewardPolicyApplicableTarget {
    private final RewardPolicyRepository rewardPolicyRepository;
}
