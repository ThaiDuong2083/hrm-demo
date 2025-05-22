package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.reward.RewardMapper;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.service.ExtenalService;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;
    private final ExtenalService extenalService;
    private final GroupRewardRepository groupRewardRepository;
    private final CommonResponseGenerator<RewardDTO, RewardGetAllDTO, RewardEntity> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createReward(RewardDTO rewardFormDTO) {
        RewardEntity rewardEntity = rewardMapper.toEntity(rewardFormDTO);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(),
                                                        MessageResponseConstant.SUCCESS,
                                                        rewardMapper.toDto(rewardEntity).getId());
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateReward(RewardDTO rewardDTO) {
        RewardEntity rewardEntity = checkRewardEntity(rewardDTO.getId());
        rewardMapper.toUpdateEntity(rewardDTO, rewardEntity);
        rewardRepository.save(rewardEntity);
        return commonResponseGenerator.returnCUResponse(
                TraceIdGenarator.getTraceId(),
                MessageResponseConstant.SUCCESS,
                rewardMapper.toDto(rewardEntity).getId());
    }

    private RewardEntity checkRewardEntity(Long id) {
        Optional<RewardEntity> rewardEntityOptional = rewardRepository.findById(id);
        if (rewardEntityOptional.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return rewardEntityOptional.get();
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
        Map<Long, BaseDTO> groupRewards = listGroupReward(page.getContent().stream().map(RewardEntity::getId).collect(Collectors.toSet()));
        List<RewardGetAllDTO> rewardDTOS = page.getContent().stream().map(entity ->{
            RewardGetAllDTO rewardGetAllDTO = rewardMapper.toGetAllDto(entity);
            rewardGetAllDTO.setGroupReward(groupRewards.get(entity.getGroupRewardId()));
            return rewardGetAllDTO;
        }).toList();

        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardDTOS, page);
    }

    @Override
    public void deleteReward(Long id) {
        try {
            RewardEntity rewardEntity = checkRewardEntity(id);
            rewardRepository.delete(rewardEntity);
            rewardRepository.flush();
        } catch (
                DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<RewardDTO> findById(Long id) {
        RewardEntity rewardEntity = checkRewardEntity(id);
        RewardDTO rewardDTO = rewardMapper.toDto(rewardEntity);
        setUomCurrency(rewardDTO);
        rewardDTO.setGroupReward(groupReward(rewardDTO.getGroupReward().getId()));
        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, rewardMapper.toDto(rewardEntity));
    }

    private void setUomCurrency(RewardDTO rewardDTO) {
        List<BaseDTO> uoms = extenalService.getUom(Set.of(rewardDTO.getUom().getId()));
        List<BaseDTO> currencys = extenalService.getCurrency(Set.of(rewardDTO.getCurrency().getId()));
        rewardDTO.setCurrency(currencys.getFirst());
        rewardDTO.setUom(uoms.getFirst());
    }
    private BaseDTO groupReward(Long groupId) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(groupId, groupRewardRepository);
    }
    private Map<Long, BaseDTO> listGroupReward (Set<Long> groupId){
        Map<Long, BaseDTO> groupRewards = new HashMap<>();
        MapperNameCode mapperNameCode = new MapperNameCode();
        List<BaseDTO> baseDTOS = mapperNameCode.convert(groupId, groupRewardRepository);
        baseDTOS.forEach(allowanceDTO->groupRewards.put(allowanceDTO.getId(), allowanceDTO));
        return groupRewards;
    }

}
