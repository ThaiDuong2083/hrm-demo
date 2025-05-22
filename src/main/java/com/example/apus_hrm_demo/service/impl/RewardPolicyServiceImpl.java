package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardPolicyEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPolicyFilter;
import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.mapper.reward_policy.RewardPolicyMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyGetAllDto;
import com.example.apus_hrm_demo.repository.RewardPolicyRepository;
import com.example.apus_hrm_demo.service.RewardPolicyApplicableTargetService;
import com.example.apus_hrm_demo.service.RewardPolicyLineService;
import com.example.apus_hrm_demo.service.RewardPolicyService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyServiceImpl implements RewardPolicyService {
    private final RewardPolicyRepository rewardPolicyRepository;
    private final RewardPolicyMapper rewardPolicyMapper;
    private final RewardPolicyLineService rewardPolicyLineService;
    private final CommonResponseGenerator<RewardPolicyDTO, RewardPolicyGetAllDto, RewardPolicyEntity> commonResponseGenerator;
    private final RewardPolicyApplicableTargetService rewardPolicyApplicableTargetService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(RewardPolicyDTO rewardPolicyDTO) {
        RewardPolicyEntity rewardPolicyEntity = rewardPolicyRepository.save(rewardPolicyMapper.toEntity(rewardPolicyDTO));

        rewardPolicyLineService.createOrUpdate(rewardPolicyDTO.getRewardPolicyLine(), rewardPolicyEntity.getId());
        if (rewardPolicyDTO.getApplicableType() != ApplicableType.ALL) {
            rewardPolicyApplicableTargetService.createOrUpdate(rewardPolicyDTO.getTarget(), rewardPolicyEntity.getId());
        }
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyMapper.toDto(rewardPolicyEntity).getId());
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(RewardPolicyDTO rewardPolicyDTO) {
        RewardPolicyEntity rewardPolicyEntity = checkEntity(rewardPolicyDTO.getId());
        ApplicableType oldApplicableType = rewardPolicyEntity.getApplicableType();

        rewardPolicyMapper.toUpdateEntity(rewardPolicyDTO, rewardPolicyEntity);
        rewardPolicyRepository.save(rewardPolicyEntity);

        updateApplicableAndLine(rewardPolicyDTO, rewardPolicyEntity, oldApplicableType);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyMapper.toDto(rewardPolicyEntity).getId());
    }

    private RewardPolicyEntity checkEntity(Long id){
        Optional<RewardPolicyEntity> rewardPolicyEntityOptional = rewardPolicyRepository.findById(id);
        if (rewardPolicyEntityOptional.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return rewardPolicyEntityOptional.get();
    }

    private void updateApplicableAndLine(RewardPolicyDTO rewardPolicyDTO, RewardPolicyEntity rewardPolicyEntity, ApplicableType oldApplicableType) {
        if ( oldApplicableType != ApplicableType.ALL) {
            rewardPolicyApplicableTargetService.delete(rewardPolicyEntity.getId());
        }
        if (rewardPolicyDTO.getApplicableType() != ApplicableType.ALL) {
            rewardPolicyApplicableTargetService.createOrUpdate(rewardPolicyDTO.getTarget(), rewardPolicyEntity.getId());
        }
        rewardPolicyLineService.createOrUpdate(rewardPolicyDTO.getRewardPolicyLine(), rewardPolicyEntity.getId());
    }

    @Override
    public BaseResponse<RewardPolicyDTO> findById(Long rewardId) {
        RewardPolicyEntity rewardPolicyEntity = checkEntity(rewardId);

        RewardPolicyDTO rewardPolicyDTO = rewardPolicyMapper.toDto(rewardPolicyEntity);
        rewardPolicyDTO.setRewardPolicyLine(rewardPolicyLineService.findByRewardPolicyId(rewardPolicyEntity.getId()));
        rewardPolicyDTO.setTarget(rewardPolicyApplicableTargetService.findByRewardPolicyId(rewardPolicyEntity.getId(), rewardPolicyEntity.getApplicableType()));

        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(),MessageResponseConstant.SUCCESS, rewardPolicyDTO);
    }

    @Override
    public BaseResponse<ResponsePage<RewardPolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter) {
        GenericSpecificationBuilder<RewardPolicyEntity> builder = CheckPolicyFilter.check(policyFliter);
        Specification<RewardPolicyEntity> spec = builder.build();
        Page<RewardPolicyEntity> page = rewardPolicyRepository.findAll(spec, pageable);
        List<RewardPolicyGetAllDto> rewardPolicyDTOList = page.stream().map(rewardPolicyMapper::toGetAllDto).toList();
        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyDTOList,page);
    }

    @Override
    public void delete(Long id) {
        RewardPolicyEntity rewardPolicyEntity = checkEntity(id);
        rewardPolicyLineService.deleteAll(rewardPolicyEntity.getId());
        rewardPolicyApplicableTargetService.delete(rewardPolicyEntity.getId());
        rewardPolicyRepository.delete(rewardPolicyEntity);
    }
}
