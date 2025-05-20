package com.example.apus_hrm_demo.mapper.reward_policy_line;

import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.reward.ConvertInReward;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.reward.RewardForPolicyLineDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConvertInRewardPolicyLine {
    private final RewardRepository rewardRepository;
    private final ConvertInReward convertInReward;

    @Named("toRewardDto")
    public BaseDTO toRewardDto(Long rewardId) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(rewardId, rewardRepository);
    }

    @Named("rewardPolicyLineDTO")
    public RewardForPolicyLineDTO rewardPolicyLineDTO(Long rewardId) {
        if (rewardId == null) {
            return null;
        }

        RewardEntity rewardEntity = rewardRepository.findById(rewardId).orElseThrow(() ->
                new NullEntityException(
                        TraceIdGenarator.getTraceId(),
                        MessageResponseConstant.NOT_FOUND
                )
        );
        BaseDTO currency = convertInReward.currencyToDTO(rewardEntity.getCurrencyId());
        BaseDTO uom = convertInReward.uomToDTO(rewardEntity.getUomId());
        BaseDTO groupReward = convertInReward.toGroupRewardDTO(rewardEntity.getGroupRewardId());
        Set<DeductionType> deductionTypeSet = convertInReward.deductionIncludeTypes(rewardEntity.getIncludeType());

        return new RewardForPolicyLineDTO(rewardEntity.getId(),
                rewardEntity.getName(),
                rewardEntity.getCode(),
                deductionTypeSet,
                currency,
                uom,
                groupReward,
                rewardEntity.getType()
                );
    }

}
