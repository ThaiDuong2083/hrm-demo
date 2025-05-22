package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.*;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.allowance.AllowanceMapper;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.payroll_line.PayrollLineMapper;
import com.example.apus_hrm_demo.mapper.reward.RewardMapper;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupForPayrollDTO;
import com.example.apus_hrm_demo.model.payroll_line.line.LineDTO;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.repository.*;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.service.PayrollLineService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.Cycle;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollLineServiceImpl implements PayrollLineService {
    private final PayrollLineRepository payrollLineRepository;
    private final PayrollLineMapper payrollLineMapper;
    private final GroupRewardRepository groupRewardRepository;
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final AllowanceRepository allowanceRepository;
    private final RewardRepository rewardRepository;
    private final AllowanceMapper allowanceMapper;
    private final RewardMapper rewardMapper;
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final RewardPolicyLineRepository rewardPolicyLineRepository;
    private final ExtenalService extenalService;

    @Override
    public void createOrUpdate(GroupForPayrollDTO groupAllowanceDTO, GroupForPayrollDTO groupRewardDTO, Long payrollId) {
        List<PayrollLineEntity> payrollLineEntities = new ArrayList<>();
        payrollLineEntities.addAll(getPayrollLineEntities(groupAllowanceDTO, PayrollLineType.ALLOWANCE, payrollId));
        payrollLineEntities.addAll(getPayrollLineEntities(groupRewardDTO, PayrollLineType.REWARD, payrollId));
        payrollLineRepository.saveAll(payrollLineEntities);
    }

    private List<PayrollLineEntity> getPayrollLineEntities(GroupForPayrollDTO groupForPayrollDTO, PayrollLineType payrollLineType, Long payrollId) {
        List<PayrollLineEntity> payrollLineEntities = new ArrayList<>();
        groupForPayrollDTO.getLines().forEach(payrollLineDTO -> {
            PayrollLineEntity payrollLineEntity;
            if (payrollLineDTO.getId() == null) {
                payrollLineEntity = payrollLineMapper.toEntity(payrollLineDTO);
                payrollLineEntity.setPayrollId(payrollId);
                payrollLineEntity.setType(payrollLineType);
            } else {
                payrollLineEntity = checkPayrollEntity(payrollLineDTO.getId());
                payrollLineMapper.toUpdateEntity(payrollLineDTO, payrollLineEntity);
            }
            payrollLineEntities.add(payrollLineEntity);
        });
        return payrollLineEntities;
    }

    private PayrollLineEntity checkPayrollEntity(Long id) {
        Optional<PayrollLineEntity> optionalPayrollLineEntity = payrollLineRepository.findById(id);
        if (optionalPayrollLineEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return optionalPayrollLineEntity.get();
    }

    @Override
    public void deleteAll(Long payrollId) {
        payrollLineRepository.deleteByPayrollId(payrollId);
    }

    @Override
    public List<PayrollLineGetAllDTO> findAllByPayrollId(Long payrollId) {
        List<PayrollLineEntity> payrollLineEntities = payrollLineRepository.findByPayrollIdAndType(payrollId);
        Set<Long> allowanceIds = getTargetIds(payrollLineEntities, PayrollLineType.ALLOWANCE);
        Set<Long> rewardIds = getTargetIds(payrollLineEntities, PayrollLineType.REWARD);

        Map<Long, String> allowanceNames = getTargetNameMap(allowanceIds, PayrollLineType.ALLOWANCE);
        Map<Long, String> rewardNames = getTargetNameMap(rewardIds, PayrollLineType.REWARD);

        Map<Long, Cycle> allowanceCycles = getCycleMap(allowanceIds, PayrollLineType.ALLOWANCE);
        Map<Long, Cycle> rewardCycles = getCycleMap(rewardIds, PayrollLineType.REWARD);

        return payrollLineEntities.stream().map(entity -> {
            PayrollLineGetAllDTO payrollLineGetAllDTO = payrollLineMapper.toGetAllDto(entity);
            if (entity.getType().equals(PayrollLineType.ALLOWANCE)) {
                payrollLineGetAllDTO.setTargetName(Objects.requireNonNull(allowanceNames).get(entity.getTargetId()));
                payrollLineGetAllDTO.setCycle(Objects.requireNonNull(allowanceCycles).get(entity.getTargetId()));
            } else if (entity.getType().equals(PayrollLineType.REWARD)) {
                payrollLineGetAllDTO.setTargetName(Objects.requireNonNull(rewardNames).get(entity.getTargetId()));
                payrollLineGetAllDTO.setCycle(Objects.requireNonNull(rewardCycles).get(entity.getTargetId()));
            }
            return payrollLineGetAllDTO;
        }).toList();
    }

    private Set<Long> getTargetIds(List<PayrollLineEntity> payrollLineEntities, PayrollLineType payrollLineType) {
        Set<Long> targetIds = new HashSet<>();
        payrollLineEntities.forEach(payrollLineEntity -> {
            if (payrollLineEntity.getType().equals(payrollLineType)) {
                targetIds.add(payrollLineEntity.getTargetId());
            }
        });
        return targetIds;
    }

    private Map<Long, String> getTargetNameMap(Set<Long> idSets, PayrollLineType type) {
        if (PayrollLineType.ALLOWANCE == type) {
            List<AllowanceEntity> allowanceEntityLists = allowanceRepository.findByIds(idSets);
            return allowanceEntityLists.stream().collect(Collectors.toMap(AllowanceEntity::getId, AllowanceEntity::getName));
        } else if (PayrollLineType.REWARD == type) {
            List<RewardEntity> rewardEntityLists = rewardRepository.findByIds(idSets);
            return rewardEntityLists.stream().collect(Collectors.toMap(RewardEntity::getId, RewardEntity::getName));
        }
        return null;
    }

    private Map<Long, Cycle> getCycleMap(Set<Long> idSets, PayrollLineType type) {
        if (PayrollLineType.ALLOWANCE == type) {
            List<AllowancePolicyLineEntity> allowancePolicyLineEntityList = allowancePolicyLineRepository.findByAllowanceIds(idSets);
            return allowancePolicyLineEntityList.stream()
                    .collect(Collectors.toMap(
                            AllowancePolicyLineEntity::getAllowanceId,
                            AllowancePolicyLineEntity::getCycle,
                            (v1, v2) -> v2
                    ));
        } else if (PayrollLineType.REWARD == type) {
            List<RewardPolicyLineEntity> rewardPolicyLineEntityList = rewardPolicyLineRepository.findByRewardIds(idSets);
            return rewardPolicyLineEntityList.stream().collect(Collectors.toMap(
                    RewardPolicyLineEntity::getRewardId,
                    RewardPolicyLineEntity::getCycle,
                    (v1, v2) -> v2
            ));
        }
        return null;
    }

    @Override
    public GroupForPayrollDTO findByPayrollId(Long payrollId, PayrollLineType type) {
        List<PayrollLineEntity> payrollLineEntities = payrollLineRepository.findByPayrollIdAndType(payrollId, type);
        Set<Long> groupIds = payrollLineEntities.stream().map(PayrollLineEntity::getGroupTargetId).collect(Collectors.toSet());
        Map<Long, BaseDTO> allGroupMaps = getlistGroup(groupIds, type);
        Map<Long, LineDTO> lineDTOMaps = getLineDTOs(payrollLineEntities.stream().map(PayrollLineEntity::getTargetId).collect(Collectors.toSet()), allGroupMaps, type);

        List<PayrollLineDTO> payrollLines = payrollLineEntities.stream().map(payrollLineEntity -> {
            PayrollLineDTO payrollLineDTO = payrollLineMapper.toDto(payrollLineEntity);
            payrollLineDTO.setLine(Objects.requireNonNull(lineDTOMaps).get(payrollLineDTO.getLine().getId()));
            return payrollLineDTO;
        }).toList();

        return new GroupForPayrollDTO(allGroupMaps.values().stream().toList(), payrollLines);
    }

    private Map<Long, LineDTO> getLineDTOs(Set<Long> lineIds, Map<Long, BaseDTO> allGroupMaps, PayrollLineType type) {
        if (!lineIds.isEmpty()) {
            if (type == PayrollLineType.REWARD) {
                return getLineRewardDTOs(lineIds, allGroupMaps);
            } else if (type == PayrollLineType.ALLOWANCE) {
                return getLineAllowanceDTOs(lineIds, allGroupMaps);
            }
        }
        return null;
    }

    private Map<Long, LineDTO> getLineAllowanceDTOs(Set<Long> lineIds, Map<Long, BaseDTO> allGroupMaps) {
        List<AllowanceEntity> allowanceEntityLists = allowanceRepository.findByIds(lineIds);
        Map<Long, BaseDTO> currencyMaps = getCurrency(allowanceEntityLists.stream().map(AllowanceEntity::getCurrencyId).collect(Collectors.toSet()));
        Map<Long, BaseDTO> uomMaps = getUom(allowanceEntityLists.stream().map(AllowanceEntity::getUomId).collect(Collectors.toSet()));

        return allowanceEntityLists.stream().map(entity -> {
            AllowanceDTO allowanceDTO = allowanceMapper.toDto(entity);
            return new LineDTO(allowanceDTO.getId(), allowanceDTO.getName(), allowanceDTO.getCode(),
                    allowanceDTO.getIncludeType(), uomMaps.get(entity.getUomId()),
                    currencyMaps.get(entity.getCurrencyId()), allGroupMaps.get(entity.getGroupAllowanceId()));
        }).collect(Collectors.toMap(LineDTO::getId, Function.identity()));
    }

    private Map<Long, LineDTO> getLineRewardDTOs(Set<Long> lineIds, Map<Long, BaseDTO> allGroupMaps) {
        List<RewardEntity> rewardEntityLists = rewardRepository.findByIds(lineIds);
        Map<Long, BaseDTO> currencyMaps = getCurrency(rewardEntityLists.stream().map(RewardEntity::getCurrencyId).collect(Collectors.toSet()));
        Map<Long, BaseDTO> uomMaps = getUom(rewardEntityLists.stream().map(RewardEntity::getUomId).collect(Collectors.toSet()));

        return rewardEntityLists.stream().map(entity -> {
            RewardDTO rewardDTO = rewardMapper.toDto(entity);
            return new LineDTO(rewardDTO.getId(), rewardDTO.getName(), rewardDTO.getCode(),
                    rewardDTO.getIncludeType(), uomMaps.get(entity.getUomId()),
                    currencyMaps.get(entity.getCurrencyId()), allGroupMaps.get(entity.getGroupRewardId()));
        }).collect(Collectors.toMap(LineDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getCurrency(Set<Long> currencyIds) {
        List<BaseDTO> currencies = extenalService.getCurrency(currencyIds);
        return currencies.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getUom(Set<Long> uomIds) {
        List<BaseDTO> uoms = extenalService.getUom(uomIds);
        return uoms.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getlistGroup(Set<Long> groupId, PayrollLineType payrollLineType) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        List<BaseDTO> baseDTOS;
        if (payrollLineType == PayrollLineType.REWARD) {
            baseDTOS = mapperNameCode.convert(groupId, groupRewardRepository);
        } else {
            baseDTOS = mapperNameCode.convert(groupId, groupAllowanceRepository);
        }
        return baseDTOS.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

}
