package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.ConverIncludeType;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.reward_policy_line.RewardPolicyLineMapper;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.reward.RewardForPolicyLineDTO;
import com.example.apus_hrm_demo.model.reward_policy_line.RewardPolicyLineDTO;
import com.example.apus_hrm_demo.repository.RewardPolicyLineRepository;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.service.RewardPolicyLineService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardPolicyLineServiceImpl implements RewardPolicyLineService {
    private final RewardPolicyLineRepository rewardPolicyLineRepository;
    private final RewardPolicyLineMapper rewardPolicyLineMapper;
    private final RewardRepository rewardRepository;
    private final ExtenalService extenalService;

    @Override
    public void createOrUpdate(List<RewardPolicyLineDTO> rewardPolicyLineDTOs, Long rewardPolicyId) {
        List<Long> rewardPolicyLineIds = new ArrayList<>();
        rewardPolicyLineDTOs.forEach(rewardPolicyLineDTO -> {
            RewardPolicyLineEntity rewardPolicyLineEntity;
            if (rewardPolicyLineDTO.getId() == null) {
                rewardPolicyLineEntity = rewardPolicyLineMapper.toEntity(rewardPolicyLineDTO);
                rewardPolicyLineEntity.setRewardPolicyId(rewardPolicyId);
            } else {
                rewardPolicyLineEntity = checkRewardPolicyLineEntity(rewardPolicyLineDTO.getId());
                rewardPolicyLineMapper.toUpdateEntity(rewardPolicyLineDTO, rewardPolicyLineEntity);
            }
            rewardPolicyLineIds.add(rewardPolicyLineRepository.save(rewardPolicyLineEntity).getId());
        });
        deleteById(rewardPolicyLineIds,rewardPolicyId);
    }

    private RewardPolicyLineEntity checkRewardPolicyLineEntity(Long id) {
        Optional<RewardPolicyLineEntity> rewardPolicyLineEntityOptional = rewardPolicyLineRepository.findById(id);
        if (rewardPolicyLineEntityOptional.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return rewardPolicyLineEntityOptional.get();
    }

    @Override
    public List<RewardPolicyLineDTO> findByRewardPolicyId(Long rewardPolicyId) {
        List<RewardPolicyLineEntity> list = rewardPolicyLineRepository.findByRewardPolicyId(rewardPolicyId);
        Map<Long, RewardForPolicyLineDTO> mapRewardForPolicyLineDTO = getMapRewardLineDto(list.stream().map(RewardPolicyLineEntity::getRewardId).collect(Collectors.toSet()));
        return list.stream().map(rewardPolicyLineEntity -> {
            RewardPolicyLineDTO rewardPolicyLineDTO = rewardPolicyLineMapper.toGetAllDto(rewardPolicyLineEntity);
            rewardPolicyLineDTO.setReward(mapRewardForPolicyLineDTO.get(rewardPolicyLineDTO.getReward().getId()));
            return rewardPolicyLineDTO;
        }).toList();
    }

    @Override
    public void deleteAll(Long rewardPolicyId) {
        rewardPolicyLineRepository.deleteByRewardPolicyId(rewardPolicyId);
    }

    @Override
    public void deleteById(List<Long> lineIds, Long rewardPolicyId) {
        List<Long> lineEntityIds = rewardPolicyLineRepository.findAllIdByRewardPolicy(rewardPolicyId);
        if (lineEntityIds.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        for (Long lineEntityId : lineEntityIds) {
            if (!lineIds.contains(lineEntityId)) {
                rewardPolicyLineRepository.deleteById(lineEntityId);
            }
        }
    }

    private Map<Long, RewardForPolicyLineDTO> getMapRewardLineDto(Set<Long> rewardIds) {
        if (rewardIds == null || rewardIds.isEmpty()) {
            return new HashMap<>();
        }

        List<RewardEntity> rewards = getRewardEntities(rewardIds);
        Map<Long, BaseDTO> currencyMap = getCurrencyMap(rewards);
        Map<Long, BaseDTO> uomMap = getUomMap(rewards);
        Map<Long, BaseDTO> groupRewardMap = getGroupRewardMap(rewards);
        Map<String, Set<DeductionType>> includeTypeMap = getIncludeTypeMap(rewards);

        return buildRewardDTOMap(rewards, currencyMap, uomMap, groupRewardMap, includeTypeMap);
    }


    private List<RewardEntity> getRewardEntities(Set<Long> ids) {
        return rewardRepository.findByIds(ids);
    }

    private Map<Long, BaseDTO> getCurrencyMap(List<RewardEntity> rewards) {
        Set<Long> currencyIds = rewards.stream().map(RewardEntity::getCurrencyId).collect(Collectors.toSet());
        List<BaseDTO> currencies = extenalService.getCurrency(currencyIds);
        return currencies.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getUomMap(List<RewardEntity> rewards) {
        Set<Long> uomIds = rewards.stream().map(RewardEntity::getUomId).collect(Collectors.toSet());
        List<BaseDTO> uoms = extenalService.getUom(uomIds);
        return uoms.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getGroupRewardMap(List<RewardEntity> rewards) {
        MapperNameCode mapper = new MapperNameCode();
        Set<Long> groupIds = rewards.stream().map(RewardEntity::getGroupRewardId).collect(Collectors.toSet());
        List<BaseDTO> groupRewards = mapper.convert(groupIds, rewardRepository);
        return groupRewards.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<String, Set<DeductionType>> getIncludeTypeMap(List<RewardEntity> rewards) {
        ConverIncludeType converter = new ConverIncludeType();
        Set<String> includeTypes = rewards.stream().map(RewardEntity::getIncludeType).collect(Collectors.toSet());
        List<Set<DeductionType>> convertedList = converter.includeTypeToListDto(includeTypes);
        return convertedList.stream().collect(Collectors.toMap(converter::includeTypeToEntity, Function.identity()));
    }

    private Map<Long, RewardForPolicyLineDTO> buildRewardDTOMap(
            List<RewardEntity> rewards,
            Map<Long, BaseDTO> currencyMap,
            Map<Long, BaseDTO> uomMap,
            Map<Long, BaseDTO> groupRewardMap,
            Map<String, Set<DeductionType>> includeTypeMap) {

        Map<Long, RewardForPolicyLineDTO> result = new HashMap<>();

        for (RewardEntity entity : rewards) {
            result.put(entity.getId(), new RewardForPolicyLineDTO(
                    entity.getId(),
                    entity.getName(),
                    entity.getCode(),
                    includeTypeMap.get(entity.getIncludeType()),
                    groupRewardMap.get(entity.getGroupRewardId()),
                    currencyMap.get(entity.getCurrencyId()),
                    uomMap.get(entity.getUomId()),
                    entity.getType()
            ));
        }

        return result;
    }

}

