package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.entity.AllowancePolicyLineEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.ConverIncludeType;
import com.example.apus_hrm_demo.mapper.allowance_policy_line.AllowancePolicyLineMapper;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.allowance.AllowanceForPolicyLineDTO;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyLineRepository;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.service.AllowancePolicyLineService;
import com.example.apus_hrm_demo.service.ExtenalService;
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
public class AllowancePolicyLineServiceImpl implements AllowancePolicyLineService {
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final AllowancePolicyLineMapper allowancePolicyLineMapper;
    private final AllowanceRepository allowanceRepository;
    private final ExtenalService extenalService;

    @Override
    public void createOrUpdate(List<AllowancePolicyLineDTO> allowancePolicyLineDTOs, Long allowancePolicyId) {
        List<Long> allowancePolicyLineIds = new ArrayList<>();
        allowancePolicyLineDTOs.forEach(allowancePolicyLineDTO -> {
            AllowancePolicyLineEntity allowancePolicyLineEntity;
            if (allowancePolicyLineDTO.getId() == null) {
                allowancePolicyLineEntity = allowancePolicyLineMapper.toEntity(allowancePolicyLineDTO);
                allowancePolicyLineEntity.setAllowancePolicyId(allowancePolicyId);
            } else {
                allowancePolicyLineEntity = checkAllowancePolicyLineEntity(allowancePolicyLineDTO.getId());
                allowancePolicyLineMapper.toUpdateEntity(allowancePolicyLineDTO, allowancePolicyLineEntity);
            }
            allowancePolicyLineIds.add(allowancePolicyLineRepository.save(allowancePolicyLineEntity).getId());
        });
        deleteById(allowancePolicyLineIds,allowancePolicyId);
    }

    private AllowancePolicyLineEntity checkAllowancePolicyLineEntity(Long id) {
        Optional<AllowancePolicyLineEntity> allowancePolicyLineEntityOptional = allowancePolicyLineRepository.findById(id);
        if (allowancePolicyLineEntityOptional.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return allowancePolicyLineEntityOptional.get();
    }

    @Override
    public List<AllowancePolicyLineDTO> findByAllowancePolicyId(Long allowancePolicyId) {
        List<AllowancePolicyLineEntity> list = allowancePolicyLineRepository.findByAllowancePolicyId(allowancePolicyId);
        Map<Long, AllowanceForPolicyLineDTO> mapAllowanceForPolicyLineDTO = getMapAllowanceLineDto(list.stream().map(AllowancePolicyLineEntity::getAllowanceId).collect(Collectors.toSet()));
        return list.stream().map(allowancePolicyLineEntity -> {
            AllowancePolicyLineDTO allowancePolicyLineDTO = allowancePolicyLineMapper.toGetAllDto(allowancePolicyLineEntity);
            allowancePolicyLineDTO.setAllowance(mapAllowanceForPolicyLineDTO.get(allowancePolicyLineDTO.getAllowance().getId()));
            return allowancePolicyLineDTO;
        }).toList();
    }

    @Override
    public void deleteAll(Long allowancePolicyId) {
        allowancePolicyLineRepository.deleteByAllowancePolicyId(allowancePolicyId);
    }

    @Override
    public void deleteById(List<Long> lineIds, Long allowancePolicyId) {
        List<Long> lineEntityIds = allowancePolicyLineRepository.findAllIdByAllowancePolicy(allowancePolicyId);
        if (lineEntityIds.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        for (Long lineEntityId : lineEntityIds) {
            if (!lineIds.contains(lineEntityId)) {
                allowancePolicyLineRepository.deleteById(lineEntityId);
            }
        }
    }

    private Map<Long, AllowanceForPolicyLineDTO> getMapAllowanceLineDto(Set<Long> allowanceIds) {
        if (allowanceIds == null || allowanceIds.isEmpty()) {
            return new HashMap<>();
        }

        List<AllowanceEntity> allowances = getAllowanceEntities(allowanceIds);
        Map<Long, BaseDTO> currencyMap = getCurrencyMap(allowances);
        Map<Long, BaseDTO> uomMap = getUomMap(allowances);
        Map<Long, BaseDTO> groupAllowanceMap = getGroupAllowanceMap(allowances);
        Map<String, Set<DeductionType>> includeTypeMap = getIncludeTypeMap(allowances);

        return buildAllowanceDTOMap(allowances, currencyMap, uomMap, groupAllowanceMap, includeTypeMap);
    }


    private List<AllowanceEntity> getAllowanceEntities(Set<Long> ids) {
        return allowanceRepository.findByIds(ids);
    }

    private Map<Long, BaseDTO> getCurrencyMap(List<AllowanceEntity> allowances) {
        Set<Long> currencyIds = allowances.stream().map(AllowanceEntity::getCurrencyId).collect(Collectors.toSet());
        List<BaseDTO> currencies = extenalService.getCurrency(currencyIds);
        return currencies.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getUomMap(List<AllowanceEntity> allowances) {
        Set<Long> uomIds = allowances.stream().map(AllowanceEntity::getUomId).collect(Collectors.toSet());
        List<BaseDTO> uoms = extenalService.getUom(uomIds);
        return uoms.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getGroupAllowanceMap(List<AllowanceEntity> allowances) {
        Set<Long> groupIds = allowances.stream().map(AllowanceEntity::getGroupAllowanceId).collect(Collectors.toSet());
        MapperNameCode mapper = new MapperNameCode();
        List<BaseDTO> groupAllowances = mapper.convert(groupIds, allowanceRepository);
        return groupAllowances.stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<String, Set<DeductionType>> getIncludeTypeMap(List<AllowanceEntity> allowances) {
        Set<String> includeTypes = allowances.stream().map(AllowanceEntity::getIncludeType).collect(Collectors.toSet());
        ConverIncludeType converter = new ConverIncludeType();
        List<Set<DeductionType>> convertedList = converter.includeTypeToListDto(includeTypes);
        return convertedList.stream().collect(Collectors.toMap(converter::includeTypeToEntity, Function.identity()));
    }

    private Map<Long, AllowanceForPolicyLineDTO> buildAllowanceDTOMap(
            List<AllowanceEntity> allowances,
            Map<Long, BaseDTO> currencyMap,
            Map<Long, BaseDTO> uomMap,
            Map<Long, BaseDTO> groupAllowanceMap,
            Map<String, Set<DeductionType>> includeTypeMap) {

        Map<Long, AllowanceForPolicyLineDTO> result = new HashMap<>();

        for (AllowanceEntity entity : allowances) {
            result.put(entity.getId(), new AllowanceForPolicyLineDTO(
                    entity.getId(),
                    entity.getName(),
                    entity.getCode(),
                    includeTypeMap.get(entity.getIncludeType()),
                    groupAllowanceMap.get(entity.getGroupAllowanceId()),
                    currencyMap.get(entity.getCurrencyId()),
                    uomMap.get(entity.getUomId()),
                    entity.getType()
            ));
        }

        return result;
    }

}

