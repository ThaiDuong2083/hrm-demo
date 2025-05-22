package com.example.apus_hrm_demo.mapper.allowance_policy_applicable_target;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AllowancePolicyApplicableTargetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowancePolicyId", ignore = true)
    AllowancePolicyApplicableTargetEntity toEntity(AllowancePolicyApplicableTargetDTO dto);

    @Mapping(target = "name", ignore = true)
    @Mapping(source = "targetId", target = "targetId")
    AllowancePolicyApplicableTargetDTO toDto(AllowancePolicyApplicableTargetEntity entity);

    @Mapping(target = "allowancePolicyId", ignore = true)
    void toUpdateEntity(AllowancePolicyApplicableTargetDTO dto,@MappingTarget AllowancePolicyApplicableTargetEntity entity);
}
