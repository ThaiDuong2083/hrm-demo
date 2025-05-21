package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.service.RewardService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    private final BaseMapper<RewardEntity, RewardDTO, RewardGetAllDTO, ResponseAfterCUDTO> rewardMapper;
    private final CommonResponseGenerator<RewardEntity, RewardDTO, RewardGetAllDTO, ResponseAfterCUDTO> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createReward(RewardDTO rewardFormDTO) {
        RewardEntity rewardEntity = rewardMapper.toEntity(rewardFormDTO);
        return commonResponseGenerator.returnCUResponse(rewardRepository.save(rewardEntity),
                TraceIdGenarator.getTraceId(),
                MessageResponseConstant.SUCCESS,
                rewardMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateReward(RewardDTO rewardFormDTO) {
        Optional<RewardEntity> check = rewardRepository.findById(rewardFormDTO.getId());
        if (check.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }

        RewardEntity newRewardEntity = check.get();
        rewardMapper.toUpdateEntity(rewardFormDTO, newRewardEntity);

        return commonResponseGenerator.returnCUResponse(rewardRepository.save(newRewardEntity),
                TraceIdGenarator.getTraceId(),
                MessageResponseConstant.SUCCESS,
                rewardMapper);
    }

    @Override
    public BaseResponse<ResponsePage<RewardGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<RewardEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<RewardEntity> spec = builder.build();
        Page<RewardEntity> page = rewardRepository.findAll(spec, pageable);

        return commonResponseGenerator.returnListResponse(page, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardMapper);
    }

    @Override
    public void deleteReward(Long id) {
        Optional<RewardEntity> rewardEntity = rewardRepository.findById(id);
        if (rewardEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        try {
            rewardRepository.delete(rewardEntity.get());
            rewardRepository.flush();
        } catch (
                DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<RewardDTO> findById(Long id) {
        Optional<RewardEntity> rewardEntity = rewardRepository.findById(id);
        if (rewardEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return commonResponseGenerator.returnReadResponse(rewardEntity.get(), TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardMapper);
    }


}
