package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardPolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.reward_policy_applicable_target.RewardPolicyApplicableTargetMapper;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.repository.RewardPolicyApplicableTargetRepository;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.service.RewardPolicyApplicableTargetService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyApplicableTargetServiceImpl implements RewardPolicyApplicableTargetService {
    private final RewardPolicyApplicableTargetRepository rewardPolicyApplicableTargetRepository;
    private final RewardPolicyApplicableTargetMapper rewardPolicyApplicableTargetMapper;
    private final ExtenalService extenalService;
    @Override
    public void createOrUpdate(List<RewardPolicyApplicableTargetDTO> rewardPolicyApplicableTargetDTOS, Long rewardPolicyId) {
        rewardPolicyApplicableTargetDTOS.forEach(rewardPolicyApplicableTargetDTO -> {
            RewardPolicyApplicableTargetEntity rewardPolicyApplicableTargetEntity;
            rewardPolicyApplicableTargetEntity = rewardPolicyApplicableTargetMapper.toEntity(rewardPolicyApplicableTargetDTO);
            rewardPolicyApplicableTargetEntity.setRewardPolicyId(rewardPolicyId);
            rewardPolicyApplicableTargetRepository.save(rewardPolicyApplicableTargetEntity);
        });
    }

    private Map<Long, BaseDTO> getApplicableTargets(Set<Long> ids, ApplicableType applicableType){
        List<BaseDTO> applicableTargets;
        switch ((applicableType)){
            case ApplicableType.EMPLOYEE -> applicableTargets= extenalService.getEmployees(ids);
            case ApplicableType.POSITION -> applicableTargets= extenalService.getPositions(ids);
            case ApplicableType.DEPARTMENT -> applicableTargets= extenalService.getDepartments(ids);
            default -> { return null;}
        }
        Map<Long,BaseDTO> applicableTargetMap = new HashMap<>();
        applicableTargets.forEach(applicableTarget -> applicableTargetMap.put(applicableTarget.getId(), applicableTarget));
        return applicableTargetMap;
    }

    @Override
    public List<RewardPolicyApplicableTargetDTO> findByRewardPolicyId(Long rewardPolicyId, ApplicableType applicableType) {
        if (applicableType == ApplicableType.ALL){
            return null;
        }

        List<RewardPolicyApplicableTargetEntity> listEntity = rewardPolicyApplicableTargetRepository.findByRewardPolicyId(rewardPolicyId);
        Map<Long,BaseDTO> applicableTargetsMap = getApplicableTargets(listEntity.stream().map(RewardPolicyApplicableTargetEntity::getTargetId).collect(Collectors.toSet()), applicableType);

        return listEntity.stream().map(entity ->{
            RewardPolicyApplicableTargetDTO rewardPolicyApplicableTargetDTO = rewardPolicyApplicableTargetMapper.toDto(entity);
            rewardPolicyApplicableTargetDTO.setName(Objects.requireNonNull(applicableTargetsMap).get(entity.getTargetId()).getName());
            return rewardPolicyApplicableTargetDTO;}).toList();
    }

    @Override
    public void delete(Long rewardPolicyId) {
        rewardPolicyApplicableTargetRepository.deleteByRewardPolicyId(rewardPolicyId);
    }

}
