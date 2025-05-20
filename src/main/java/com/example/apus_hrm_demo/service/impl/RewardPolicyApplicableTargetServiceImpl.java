package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardPolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.BaseOtherSVEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.reward_policy_applicable_target.RewardPolicyApplicableTargetMapper;
import com.example.apus_hrm_demo.model.reward_policy_applicable_target.RewardPolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.repository.RewardPolicyApplicableTargetRepository;
import com.example.apus_hrm_demo.service.RewardPolicyApplicableTargetService;
import com.example.apus_hrm_demo.service.ApplicableTargetService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyApplicableTargetServiceImpl implements RewardPolicyApplicableTargetService {
    private final RewardPolicyApplicableTargetRepository rewardPolicyApplicableTargetRepository;
    private final RewardPolicyApplicableTargetMapper rewardPolicyApplicableTargetMapper;
    private final ApplicableTargetService applicableTargetService;

    @Override
    public void create(RewardPolicyApplicableTargetDetailDTO rewardPolicyApplicableTargetDetailDTO, Long rewardPolicyId) {
        RewardPolicyApplicableTargetEntity rewardPolicyApplicableTargetEntity = rewardPolicyApplicableTargetMapper.toEntity(rewardPolicyApplicableTargetDetailDTO);
        rewardPolicyApplicableTargetEntity.setRewardPolicyId(rewardPolicyId);
        rewardPolicyApplicableTargetRepository.save(rewardPolicyApplicableTargetEntity);
    }

    @Override
    public void update(RewardPolicyApplicableTargetDetailDTO rewardPolicyApplicableTargetDetailDTO, Long rewardPolicyId) {
        if (rewardPolicyApplicableTargetDetailDTO.getId() == null) {
            create(rewardPolicyApplicableTargetDetailDTO,rewardPolicyId);
        }else {
            Optional<RewardPolicyApplicableTargetEntity> rewardPolicyApplicableTargetEntityOptional = rewardPolicyApplicableTargetRepository.findById(rewardPolicyApplicableTargetDetailDTO.getId());
            if (rewardPolicyApplicableTargetEntityOptional.isEmpty()){
                throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
            }
            RewardPolicyApplicableTargetEntity rewardPolicyApplicableTargetEntity = rewardPolicyApplicableTargetEntityOptional.get();
            rewardPolicyApplicableTargetMapper.toUpdateEntity(rewardPolicyApplicableTargetDetailDTO, rewardPolicyApplicableTargetEntity);
            rewardPolicyApplicableTargetRepository.save(rewardPolicyApplicableTargetEntity);
        }
    }

    @Override
    public List<RewardPolicyApplicableTargetDetailDTO> findByRewardPolicyId(Long rewardPolicyId, ApplicableType applicableType) {
        if (applicableType == ApplicableType.ALL){
            return null;
        }
        List<RewardPolicyApplicableTargetEntity> listEntity = rewardPolicyApplicableTargetRepository.findByRewardPolicyId(rewardPolicyId);
        List<BaseOtherSVEntity> listTargets = applicableTargetService.getApplicableTargets(listEntity.stream().map(RewardPolicyApplicableTargetEntity::getTargetId).toList(), applicableType);

        List<RewardPolicyApplicableTargetDetailDTO> listDtos = new ArrayList<>();
        RewardPolicyApplicableTargetDetailDTO dto;
        for (int i = 0; i < listTargets.size(); i++) {
            dto = rewardPolicyApplicableTargetMapper.toGetAllDto(listEntity.get(i));
            dto.setTargetId(listTargets.get(i).getId());
            dto.setName(listTargets.get(i).getName());
            listDtos.add(dto);
        }

        return listDtos;
    }

    @Override
    public void delete(Long rewardId) {
        List<RewardPolicyApplicableTargetEntity> list = rewardPolicyApplicableTargetRepository.findByRewardPolicyId(rewardId);
        if (list.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        list.stream().iterator().forEachRemaining(rewardPolicyApplicableTargetRepository::delete);
    }

}
