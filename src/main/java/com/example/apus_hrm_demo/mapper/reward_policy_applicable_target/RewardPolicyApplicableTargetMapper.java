package com.example.apus_hrm_demo.mapper.reward_policy_applicable_target;

import com.example.apus_hrm_demo.entity.RewardPolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RewardPolicyApplicableTargetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rewardPolicyId", ignore = true)
    RewardPolicyApplicableTargetEntity toEntity(RewardPolicyApplicableTargetDTO dto);

    @Mapping(target = "name", ignore = true)
    @Mapping(source = "targetId", target = "targetId")
    RewardPolicyApplicableTargetDTO toDto(RewardPolicyApplicableTargetEntity entity);

    @Mapping(target = "rewardPolicyId", ignore = true)
    void toUpdateEntity(RewardPolicyApplicableTargetDTO dto,@MappingTarget RewardPolicyApplicableTargetEntity entity);
}
