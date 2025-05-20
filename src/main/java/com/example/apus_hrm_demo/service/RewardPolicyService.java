package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDetailDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import org.springframework.data.domain.Pageable;

public interface RewardPolicyService {
    BaseResponse<ResponseAfterCUDTO> create(RewardPolicyDTO rewardPolicyDTO);
    BaseResponse<ResponseAfterCUDTO> update( RewardPolicyDTO rewardPolicyDTO);
    BaseResponse<RewardPolicyDetailDTO> findById(Long rewardId);
    BaseResponse<ResponsePage<RewardPolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter);
    void delete(Long rewardId);

}
