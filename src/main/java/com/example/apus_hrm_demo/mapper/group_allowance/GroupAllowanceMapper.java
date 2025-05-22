package com.example.apus_hrm_demo.mapper.group_allowance;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceDTO;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupAllowanceMapper {
    @Mapping(source = "parentId", target = "parent.id")
    GroupAllowanceDTO toDto(GroupAllowanceEntity groupAllowanceEntity);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    GroupAllowanceEntity toEntity(GroupAllowanceDTO groupAllowanceDTO);

    GroupAllowanceGetAllDTO toGetAllDto(GroupAllowanceEntity entity);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(GroupAllowanceDTO groupAllowanceDTO,@MappingTarget GroupAllowanceEntity groupAllowanceEntity);
}
