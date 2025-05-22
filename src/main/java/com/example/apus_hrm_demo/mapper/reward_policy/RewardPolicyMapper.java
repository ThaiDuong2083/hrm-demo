package com.example.apus_hrm_demo.mapper.reward_policy;

import com.example.apus_hrm_demo.entity.RewardPolicyEntity;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyGetAllDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RewardPolicyMapper {
    RewardPolicyGetAllDto toGetAllDto(RewardPolicyEntity entity);

    @Mapping(target = "rewardPolicyLine", ignore = true)
    @Mapping(target = "target", ignore = true)
    RewardPolicyDTO toDto(RewardPolicyEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RewardPolicyEntity toEntity(RewardPolicyDTO dto);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toUpdateEntity(RewardPolicyDTO dto, @MappingTarget RewardPolicyEntity entity);
}
