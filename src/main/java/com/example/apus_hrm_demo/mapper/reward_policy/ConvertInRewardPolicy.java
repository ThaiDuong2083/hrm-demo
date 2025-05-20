package com.example.apus_hrm_demo.mapper.reward_policy;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import com.example.apus_hrm_demo.mapper.reward_policy_line.RewardPolicyLineMapper;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineGetAllDTO;
import com.example.apus_hrm_demo.repository.RewardPolicyLineRepository;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertInRewardPolicy {
    private final RewardPolicyLineRepository rewardPolicyLineRepository;
    private final RewardPolicyLineMapper rewardPolicyLineMapper;

    @Named("convertToRewardPolicyLineDTO")
    public List<RewardPolicyLineGetAllDTO> convertToRewardPolicyLineDTO(Long rewardPolicyId) {
        GenericSpecificationBuilder<RewardPolicyLineEntity> builder = new GenericSpecificationBuilder<>();
        if (rewardPolicyId == null) {
            return new ArrayList<>();
        }
        builder.with("rewardPolicyId", SearchOperation.EQUALITY, rewardPolicyId, false);
        Specification<RewardPolicyLineEntity> spec = builder.build();
        List<RewardPolicyLineEntity> list = rewardPolicyLineRepository.findAll(spec);
        return list.stream().map(rewardPolicyLineMapper::toGetAllDto).toList();
    }
}
