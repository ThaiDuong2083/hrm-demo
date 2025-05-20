package com.example.apus_hrm_demo.mapper.group_allowance;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ParentGroupAllowanceMapper.class)
public interface GroupAllowanceMapper extends BaseMapper<GroupAllowanceEntity, GroupAllowanceDTO,GroupAllowanceDTO, ResponseAfterCUDTO> {
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "parentIdAllowanceToParentDto")
    GroupAllowanceDTO toDto(GroupAllowanceEntity groupAllowanceEntity);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    GroupAllowanceEntity toEntity(GroupAllowanceDTO groupAllowanceDTO);

    @Mapping(source = "parentId", target = "parent", qualifiedByName = "parentIdAllowanceToParentDto")
    GroupAllowanceDTO toGetAllDto(GroupAllowanceEntity entity);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(GroupAllowanceDTO groupAllowanceDTO,@MappingTarget GroupAllowanceEntity groupAllowanceEntity);
}
