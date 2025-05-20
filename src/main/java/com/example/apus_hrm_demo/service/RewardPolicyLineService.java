package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineGetAllDTO;

import java.util.List;

public interface RewardPolicyLineService {
    void create(RewardPolicyLineDTO rewardPolicyLineDTOS, Long rewardPolicyId);
    void update(RewardPolicyLineDTO rewardPolicyLineDTOS, Long rewardPolicyId);
    List<RewardPolicyLineGetAllDTO> findByRewardPolicyId(Long rewardPolicyId);
    void deleteAll(Long rewardPolicyId);

}
