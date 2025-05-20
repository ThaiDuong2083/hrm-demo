package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.reward_policy_line.RewardPolicyLineMapper;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineGetAllDTO;
import com.example.apus_hrm_demo.repository.RewardPolicyLineRepository;
import com.example.apus_hrm_demo.service.RewardPolicyLineService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyLineServiceImpl implements RewardPolicyLineService {
    private final RewardPolicyLineRepository rewardPolicyLineRepository;
    private final RewardPolicyLineMapper rewardPolicyLineMapper;

    @Override
    public void create(RewardPolicyLineDTO rewardPolicyLineDTO, Long rewardPolicyId) {
            RewardPolicyLineEntity rewardPolicyLineEntity = rewardPolicyLineMapper.toEntity(rewardPolicyLineDTO);
            rewardPolicyLineEntity.setRewardPolicyId(rewardPolicyId);
            rewardPolicyLineRepository.save(rewardPolicyLineEntity);
    }

    @Override
    public void update(RewardPolicyLineDTO rewardPolicyLineDTO, Long rewardPolicyId) {
        if (rewardPolicyLineDTO.getId() == null) {
            create(rewardPolicyLineDTO, rewardPolicyId);
        } else {
            Optional<RewardPolicyLineEntity> rewardPolicyLineEntityOptional = rewardPolicyLineRepository.findById(rewardPolicyLineDTO.getId());
            if (rewardPolicyLineEntityOptional.isEmpty()) {
                throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
            }
            RewardPolicyLineEntity rewardPolicyLineEntity = rewardPolicyLineEntityOptional.get();
            rewardPolicyLineMapper.toUpdateEntity(rewardPolicyLineDTO, rewardPolicyLineEntity);
            rewardPolicyLineRepository.save(rewardPolicyLineEntity);
        }
    }
    @Override
    public List<RewardPolicyLineGetAllDTO> findByRewardPolicyId(Long rewardPolicyId) {
        List<RewardPolicyLineEntity> list = rewardPolicyLineRepository.findByRewardPolicyId(rewardPolicyId);
        return list.stream().map(rewardPolicyLineMapper::toGetAllDto).toList();
    }

    @Override
    public void deleteAll(Long rewardPolicyId) {
        List<RewardPolicyLineEntity> list = rewardPolicyLineRepository.findByRewardPolicyId(rewardPolicyId);
        if (!list.isEmpty()){
            list.stream().iterator().forEachRemaining(rewardPolicyLineRepository::delete);
        }
    }
}

