package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;

import java.util.List;

public interface RewardPolicyLineService {
    void createOrUpdate(List<RewardPolicyLineDTO> rewardPolicyLineDTOS, Long rewardPolicyId);
    List<RewardPolicyLineDTO> findByRewardPolicyId(Long rewardPolicyId);
    void deleteAll(Long rewardPolicyId);

    void deleteById(List<Long> lineIds, Long rewardPolicyId);
}
