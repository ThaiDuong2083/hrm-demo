package com.example.apus_hrm_demo.mapper.reward_policy_line;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RewardPolicyLineMapper {

    @Mapping(target = "rewardId", source = "reward.id")
    @Mapping(target = "rewardPolicyId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    RewardPolicyLineEntity toEntity(RewardPolicyLineDTO dto);

    @Mapping(target = "rewardId", source = "reward.id")
    @Mapping(target = "rewardPolicyId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(RewardPolicyLineDTO dto, @MappingTarget RewardPolicyLineEntity entity);

    @Mapping(target = "reward.id", source = "rewardId")
    RewardPolicyLineDTO toGetAllDto(RewardPolicyLineEntity entity);
}
