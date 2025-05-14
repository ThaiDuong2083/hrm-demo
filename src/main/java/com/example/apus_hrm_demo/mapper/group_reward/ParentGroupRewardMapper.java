package com.example.apus_hrm_demo.mapper.group_reward;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentGroupRewardMapper {
    private final GroupRewardRepository groupRewardRepository;

    @Named("parentIdRewardToParentDto")
    public BaseDTO parentIdToParentDto(Long parentId) {
        if (parentId == null) {
            return null;
        }

        GroupRewardEntity parentEntity = groupRewardRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent Id: " + parentId + " not found"));

        BaseDTO dto = new BaseDTO();
        dto.setId(parentEntity.getId());
        dto.setName(parentEntity.getName());
        dto.setCode(parentEntity.getCode());
        return dto;
    }
}