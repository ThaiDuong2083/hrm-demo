package com.example.apus_hrm_demo.mapper.reward_policy_line;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineGetAllDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInRewardPolicyLine.class)
public interface RewardPolicyLineMapper extends BaseMapper<RewardPolicyLineEntity, RewardPolicyLineDTO, RewardPolicyLineGetAllDTO, ResponseAfterCUDTO> {

    @Mapping(source = "rewardId", target = "reward", qualifiedByName = "toRewardDto")
    RewardPolicyLineDTO toDto(RewardPolicyLineEntity entity);

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

    @Mapping(target = "reward", source = "rewardId", qualifiedByName = "rewardPolicyLineDTO")
    RewardPolicyLineGetAllDTO toGetAllDto(RewardPolicyLineEntity entity);
}
