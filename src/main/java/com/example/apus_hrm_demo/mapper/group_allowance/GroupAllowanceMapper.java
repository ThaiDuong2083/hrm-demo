package com.example.apus_hrm_demo.mapper.group_allowance;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.model.GroupAllowanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ParentGroupAllowanceMapper.class)
public interface GroupAllowanceMapper {
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "parentIdAllowanceToParentDto")
    GroupAllowanceDTO toDto(GroupAllowanceEntity groupAllowanceEntity);

    @Mapping(source = "parent.id",target = "parentId")
    GroupAllowanceEntity toEntity(GroupAllowanceDTO groupAllowanceDTO);

    @Mapping(source = "parent.id",target = "parentId")
    void toUpdateEntity(GroupAllowanceDTO groupAllowanceDTO,@MappingTarget GroupAllowanceEntity groupAllowanceEntity);
}
