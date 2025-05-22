package com.example.apus_hrm_demo.mapper.allowance_policy;

import com.example.apus_hrm_demo.entity.AllowancePolicyEntity;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AllowancePolicyMapper {
    AllowancePolicyGetAllDto toGetAllDto(AllowancePolicyEntity entity);

    @Mapping(target = "allowancePolicyLine", ignore = true)
    @Mapping(target = "target", ignore = true)
    AllowancePolicyDTO toDto(AllowancePolicyEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AllowancePolicyEntity toEntity(AllowancePolicyDTO dto);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toUpdateEntity(AllowancePolicyDTO dto, @MappingTarget AllowancePolicyEntity entity);
}
