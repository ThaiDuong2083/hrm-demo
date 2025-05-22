package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.mapper.allowance_policy_applicable_target.AllowancePolicyApplicableTargetMapper;
import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyApplicableTargetRepository;
import com.example.apus_hrm_demo.service.AllowancePolicyApplicableTargetService;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AllowancePolicyApplicableTargetServiceImpl implements AllowancePolicyApplicableTargetService {
    private final AllowancePolicyApplicableTargetRepository allowancePolicyApplicableTargetRepository;
    private final AllowancePolicyApplicableTargetMapper allowancePolicyApplicableTargetMapper;
    private final ExtenalService extenalService;
    @Override
    public void createOrUpdate(List<AllowancePolicyApplicableTargetDTO> allowancePolicyApplicableTargetDTOS, Long allowancePolicyId) {
        List<AllowancePolicyApplicableTargetEntity> list = new ArrayList<>();
        allowancePolicyApplicableTargetDTOS.forEach(allowancePolicyApplicableTargetDTO -> {
            AllowancePolicyApplicableTargetEntity allowancePolicyApplicableTargetEntity;
                allowancePolicyApplicableTargetEntity = allowancePolicyApplicableTargetMapper.toEntity(allowancePolicyApplicableTargetDTO);
                allowancePolicyApplicableTargetEntity.setAllowancePolicyId(allowancePolicyId);
                list.add(allowancePolicyApplicableTargetEntity);
        });
        allowancePolicyApplicableTargetRepository.saveAll(list);
    }

    private Map<Long,BaseDTO> getApplicableTargets(Set<Long>ids, ApplicableType applicableType){
        List<BaseDTO> applicableTargets;
        Map<Long,BaseDTO> applicableTargetMap = new HashMap<>();
        switch (applicableType){
            case ApplicableType.EMPLOYEE -> applicableTargets= extenalService.getEmployees(ids);
            case ApplicableType.POSITION -> applicableTargets= extenalService.getPositions(ids);
            case ApplicableType.DEPARTMENT -> applicableTargets= extenalService.getDepartments(ids);
            default -> { return null;}
        }
        applicableTargets.forEach(applicableTarget -> applicableTargetMap.put(applicableTarget.getId(), applicableTarget));
        return applicableTargetMap;
    }

    @Override
    public List<AllowancePolicyApplicableTargetDTO> findByAllowancePolicyId(Long allowancePolicyId, ApplicableType applicableType) {
        if (applicableType == ApplicableType.ALL){
            return null;
        }

        List<AllowancePolicyApplicableTargetEntity> listEntity = allowancePolicyApplicableTargetRepository.findByAllowancePolicyId(allowancePolicyId);
        Map<Long,BaseDTO> applicableTargetsMap = getApplicableTargets(listEntity.stream().map(AllowancePolicyApplicableTargetEntity::getTargetId).collect(Collectors.toSet()), applicableType);

        return listEntity.stream().map(entity ->{
            AllowancePolicyApplicableTargetDTO allowancePolicyApplicableTargetDTO = allowancePolicyApplicableTargetMapper.toDto(entity);
            allowancePolicyApplicableTargetDTO.setName(Objects.requireNonNull(applicableTargetsMap).get(entity.getTargetId()).getName());
            return allowancePolicyApplicableTargetDTO;}).toList();
    }

    @Override
    public void delete(Long allowancePolicyId) {
        allowancePolicyApplicableTargetRepository.deleteByAllowancePolicyId(allowancePolicyId);
    }

}
