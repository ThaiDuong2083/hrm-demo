package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import org.springframework.data.domain.Pageable;

public interface RewardService {
    BaseResponse<ResponseAfterCUDTO> createReward(RewardDTO rewardFormDTO);
    BaseResponse<ResponseAfterCUDTO> updateReward(RewardDTO rewardFormDTO);

    BaseResponse<ResponsePage<RewardGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable);
    void deleteReward(Long id);
    BaseResponse<RewardDTO> findById(Long id);
}
