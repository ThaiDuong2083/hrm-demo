package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardDTO;
import org.springframework.data.domain.Pageable;

public interface GroupRewardService {
    BaseResponse<ResponseAfterCUDTO> create(GroupRewardDTO groupRewardDTO);
    BaseResponse<ResponseAfterCUDTO> update(GroupRewardDTO groupRewardDTO);
    void delete(Long id);
    BaseResponse<GroupRewardDTO> findById(Long id);
    BaseResponse<ResponsePage<GroupRewardDTO>> getAll(String name, Boolean isActive, Pageable pageable);
}
