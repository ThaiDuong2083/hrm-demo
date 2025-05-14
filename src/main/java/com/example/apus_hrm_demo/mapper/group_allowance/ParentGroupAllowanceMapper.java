package com.example.apus_hrm_demo.mapper.group_allowance;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentGroupAllowanceMapper {
    private final GroupAllowanceRepository groupAllowanceRepository;

    @Named("parentIdAllowanceToParentDto")
    public BaseDTO parentIdToParentDto(Long parentId) {
        if (parentId == null) {
            return null;
        }

        GroupAllowanceEntity parentEntity = groupAllowanceRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent Id: " + parentId + " not found"));

        BaseDTO dto = new BaseDTO();
        dto.setId(parentEntity.getId());
        dto.setName(parentEntity.getName());
        dto.setCode(parentEntity.getCode());
        return dto;
    }
}