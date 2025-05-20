package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardPolicyEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPolicyFilter;
import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.mapper.reward_policy.RewardPolicyMapper;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyDetailDTO;
import com.example.apus_hrm_demo.model.reward_policy.RewardPolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.RewardPolicyRepository;
import com.example.apus_hrm_demo.service.RewardPolicyApplicableTargetService;
import com.example.apus_hrm_demo.service.RewardPolicyLineService;
import com.example.apus_hrm_demo.service.RewardPolicyService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import com.example.apus_hrm_demo.util.i18n.GenerateMessage;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyServiceImpl implements RewardPolicyService {
    private final RewardPolicyRepository rewardPolicyRepository;
    private final RewardPolicyMapper rewardPolicyMapper;
    private final RewardPolicyLineService rewardPolicyLineService;
    private final CommonResponseGenerator<RewardPolicyEntity, RewardPolicyDetailDTO, RewardPolicyGetAllDto, ResponseAfterCUDTO> commonResponseGenerator;
    private final HttpServletRequest request;
    private final GenerateMessage generateMessage;
    private final RewardPolicyApplicableTargetService rewardPolicyApplicableTargetService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(RewardPolicyDTO rewardPolicyDTO) {
        RewardPolicyEntity rewardPolicyEntity = rewardPolicyRepository.save(rewardPolicyMapper.toEntity(rewardPolicyDTO));
        int length = (rewardPolicyDTO.getRewardPolicyLine().size()<= rewardPolicyDTO.getTarget().size()) ? rewardPolicyDTO.getTarget().size()-1 : rewardPolicyDTO.getRewardPolicyLine().size()-1;

        for(int i=0;i<=length;i++){
            if(i < rewardPolicyDTO.getRewardPolicyLine().size()){
                rewardPolicyLineService.create(rewardPolicyDTO.getRewardPolicyLine().get(i),rewardPolicyEntity.getId());
            }
            if(i < rewardPolicyDTO.getTarget().size() && rewardPolicyDTO.getApplicableType()!= ApplicableType.ALL){
                rewardPolicyApplicableTargetService.create(rewardPolicyDTO.getTarget().get(i),rewardPolicyEntity.getId());
            }
        }
        return commonResponseGenerator.returnCUResponse(rewardPolicyEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(RewardPolicyDTO rewardPolicyDTO) {
        Optional<RewardPolicyEntity> rewardPolicyEntityOptional = rewardPolicyRepository.findById(rewardPolicyDTO.getId());
        if (rewardPolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        RewardPolicyEntity rewardPolicyEntity = rewardPolicyEntityOptional.get();
        ApplicableType oldApplicableType = rewardPolicyEntity.getApplicableType();
        Boolean checkApplicable = (rewardPolicyEntity.getApplicableType() == rewardPolicyDTO.getApplicableType());

        rewardPolicyMapper.toUpdateEntity(rewardPolicyDTO, rewardPolicyEntity);
        rewardPolicyRepository.save(rewardPolicyEntity);
        updateApplicabletarget(rewardPolicyDTO,rewardPolicyEntity, checkApplicable, oldApplicableType);
        return commonResponseGenerator.returnCUResponse(rewardPolicyEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyMapper);
    }

    private void updateApplicabletarget(RewardPolicyDTO rewardPolicyDTO, RewardPolicyEntity rewardPolicyEntity, Boolean checkApplicable, ApplicableType oldApplicableType) {
        int length = (rewardPolicyDTO.getRewardPolicyLine().size()<= rewardPolicyDTO.getTarget().size()) ? rewardPolicyDTO.getRewardPolicyLine().size()-1 : rewardPolicyDTO.getTarget().size()-1;
        if(Boolean.FALSE.equals(checkApplicable) && oldApplicableType!= ApplicableType.ALL) {
            rewardPolicyApplicableTargetService.delete(rewardPolicyEntity.getId());
        }
        for (int i = 0; i <= length; i++) {
            if (i < rewardPolicyDTO.getRewardPolicyLine().size()) {
                rewardPolicyLineService.update(rewardPolicyDTO.getRewardPolicyLine().get(i), rewardPolicyEntity.getId());
            }
            if (i < rewardPolicyDTO.getTarget().size() && rewardPolicyDTO.getApplicableType()!= ApplicableType.ALL) {
                if (rewardPolicyDTO.getApplicableType()!= rewardPolicyEntity.getApplicableType()) {
                    rewardPolicyApplicableTargetService.create(rewardPolicyDTO.getTarget().get(i), rewardPolicyEntity.getId());
                }else {
                    rewardPolicyApplicableTargetService.update(rewardPolicyDTO.getTarget().get(i), rewardPolicyEntity.getId());
                }
            }
        }
    }

    @Override
    public BaseResponse<RewardPolicyDetailDTO> findById(Long rewardId) {
        Optional<RewardPolicyEntity> rewardPolicyEntityOptional = rewardPolicyRepository.findById(rewardId);

        if (rewardPolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }

        RewardPolicyEntity rewardPolicyEntity = rewardPolicyEntityOptional.get();

        RewardPolicyDetailDTO rewardPolicyDetailDTO = rewardPolicyMapper.toDto(rewardPolicyEntity);
        rewardPolicyDetailDTO.setRewardPolicyLine(rewardPolicyLineService.findByRewardPolicyId(rewardPolicyEntity.getId()));
        rewardPolicyDetailDTO.setTarget(rewardPolicyApplicableTargetService.findByRewardPolicyId(rewardPolicyEntity.getId(), rewardPolicyEntity.getApplicableType()));

        BaseResponse<RewardPolicyDetailDTO> response = new BaseResponse<>();
        response.setData(rewardPolicyDetailDTO);
        response.setTraceId(TraceIdGenarator.getTraceId());
        response.setMessage(generateMessage.getMessage(MessageResponseConstant.SUCCESS, request.getLocale()));
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<RewardPolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter) {
        GenericSpecificationBuilder<RewardPolicyEntity> builder = CheckPolicyFilter.check(policyFliter);
        Specification<RewardPolicyEntity> spec = builder.build();
        Page<RewardPolicyEntity> rewardPolicyEntities = rewardPolicyRepository.findAll(spec,pageable);
        return commonResponseGenerator.returnListResponse(rewardPolicyEntities,TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardPolicyMapper);
    }

    @Override
    public void delete(Long rewardId) {
        Optional<RewardPolicyEntity> rewardPolicyEntityOptional = rewardPolicyRepository.findById(rewardId);
        if (rewardPolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        RewardPolicyEntity rewardPolicyEntity = rewardPolicyEntityOptional.get();
        rewardPolicyLineService.deleteAll(rewardPolicyEntity.getId());
        rewardPolicyApplicableTargetService.delete(rewardPolicyEntity.getId());
        rewardPolicyRepository.delete(rewardPolicyEntity);
    }
}
