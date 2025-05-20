package com.example.apus_hrm_demo.mapper.allowance_policy_applicable_target;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInAllowancePolicyApplicableTarget.class)
public interface AllowancePolicyApplicableTargetMapper extends BaseMapper<AllowancePolicyApplicableTargetEntity,
                                                                        AllowancePolicyApplicableTargetDTO,
                                                                        AllowancePolicyApplicableTargetDetailDTO,
                                                                        ResponseAfterCUDTO> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "allowancePolicyId", ignore = true)
    AllowancePolicyApplicableTargetEntity toEntity(AllowancePolicyApplicableTargetDetailDTO dto);

    @Mapping(target = "name", ignore = true)
    @Mapping(source = "targetId", target = "targetId")
    AllowancePolicyApplicableTargetDetailDTO toDto(AllowancePolicyApplicableTargetEntity entity, @Context Long allowancePolicyId);

    @Mapping(target = "allowancePolicyId", ignore = true)
    void toUpdateEntity(AllowancePolicyApplicableTargetDetailDTO dto,@MappingTarget AllowancePolicyApplicableTargetEntity entity);
}
