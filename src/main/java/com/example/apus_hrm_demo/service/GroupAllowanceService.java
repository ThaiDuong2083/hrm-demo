package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceDTO;
import org.springframework.data.domain.Pageable;

public interface GroupAllowanceService {
    BaseResponse<ResponseAfterCUDTO> create(GroupAllowanceDTO groupAllowanceDTO);
    BaseResponse<ResponseAfterCUDTO> update(GroupAllowanceDTO groupAllowanceDTO);
    void delete(Long id);
    BaseResponse<GroupAllowanceDTO> findById(Long id);
    BaseResponse<ResponsePage<GroupAllowanceDTO>> getAll(String name, Boolean isActive, Pageable pageable);
}
