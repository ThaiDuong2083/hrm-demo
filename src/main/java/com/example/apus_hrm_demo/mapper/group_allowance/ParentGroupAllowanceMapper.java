package com.example.apus_hrm_demo.mapper.group_allowance;

import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
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
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(parentId,groupAllowanceRepository);
    }
}