package com.example.apus_hrm_demo.mapper.reward_policy_applicable_target;

import com.example.apus_hrm_demo.entity.RewardPolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInRewardPolicyApplicableTarget.class)
public interface RewardPolicyApplicableTargetMapper extends BaseMapper<RewardPolicyApplicableTargetEntity,
                                                                        RewardPolicyApplicableTargetDTO,
                                                                        RewardPolicyApplicableTargetDetailDTO,
                                                                        ResponseAfterCUDTO> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rewardPolicyId", ignore = true)
    RewardPolicyApplicableTargetEntity toEntity(RewardPolicyApplicableTargetDetailDTO dto);

    @Mapping(target = "name", ignore = true)
    @Mapping(source = "targetId", target = "targetId")
    RewardPolicyApplicableTargetDetailDTO toDto(RewardPolicyApplicableTargetEntity entity, @Context Long rewardPolicyId);

    @Mapping(target = "rewardPolicyId", ignore = true)
    void toUpdateEntity(RewardPolicyApplicableTargetDetailDTO dto,@MappingTarget RewardPolicyApplicableTargetEntity entity);
}
