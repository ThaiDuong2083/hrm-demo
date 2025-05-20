package com.example.apus_hrm_demo.mapper.group_reward;

import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
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
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(parentId,groupRewardRepository);
    }
}