package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;

import java.util.List;

public interface RewardPolicyApplicableTargetService {
    void createOrUpdate(List<RewardPolicyApplicableTargetDTO> rewardPolicyApplicableTargetDTOS, Long rewardPolicyId);
    List<RewardPolicyApplicableTargetDTO> findByRewardPolicyId(Long rewardPolicyId, ApplicableType applicableType);
    void delete(Long rewardId);
}
