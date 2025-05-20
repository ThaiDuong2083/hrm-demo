package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.BaseOtherSVEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.allowance_policy_applicable_target.AllowancePolicyApplicableTargetMapper;
import com.example.apus_hrm_demo.model.allowance_policy_applicable_target.AllowancePolicyApplicableTargetDetailDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyApplicableTargetRepository;
import com.example.apus_hrm_demo.service.AllowancePolicyApplicableTargetService;
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
public class AllowancePolicyApplicableTargetServiceImpl implements AllowancePolicyApplicableTargetService {
    private final AllowancePolicyApplicableTargetRepository allowancePolicyApplicableTargetRepository;
    private final AllowancePolicyApplicableTargetMapper allowancePolicyApplicableTargetMapper;
    private final ApplicableTargetService applicableTargetService;

    @Override
    public void create(AllowancePolicyApplicableTargetDetailDTO allowancePolicyApplicableTargetDetailDTO, Long allowancePolicyId) {
        AllowancePolicyApplicableTargetEntity allowancePolicyApplicableTargetEntity = allowancePolicyApplicableTargetMapper.toEntity(allowancePolicyApplicableTargetDetailDTO);
        allowancePolicyApplicableTargetEntity.setAllowancePolicyId(allowancePolicyId);
        allowancePolicyApplicableTargetRepository.save(allowancePolicyApplicableTargetEntity);
    }

    @Override
    public void update(AllowancePolicyApplicableTargetDetailDTO allowancePolicyApplicableTargetDetailDTO, Long allowancePolicyId) {
        if (allowancePolicyApplicableTargetDetailDTO.getId() == null) {
            create(allowancePolicyApplicableTargetDetailDTO,allowancePolicyId);
        }else {
            Optional<AllowancePolicyApplicableTargetEntity> allowancePolicyApplicableTargetEntityOptional = allowancePolicyApplicableTargetRepository.findById(allowancePolicyApplicableTargetDetailDTO.getId());
            if (allowancePolicyApplicableTargetEntityOptional.isEmpty()){
                throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
            }
            AllowancePolicyApplicableTargetEntity allowancePolicyApplicableTargetEntity = allowancePolicyApplicableTargetEntityOptional.get();
            allowancePolicyApplicableTargetMapper.toUpdateEntity(allowancePolicyApplicableTargetDetailDTO, allowancePolicyApplicableTargetEntity);
            allowancePolicyApplicableTargetRepository.save(allowancePolicyApplicableTargetEntity);
        }
    }

    @Override
    public List<AllowancePolicyApplicableTargetDetailDTO> findByAllowancePolicyId(Long allowancePolicyId, ApplicableType applicableType) {
        if (applicableType == ApplicableType.ALL){
            return null;
        }

        List<AllowancePolicyApplicableTargetEntity> listEntity = allowancePolicyApplicableTargetRepository.findByAllowancePolicyId(allowancePolicyId);
        List<BaseOtherSVEntity> listTargets = applicableTargetService.getApplicableTargets(listEntity.stream().map(AllowancePolicyApplicableTargetEntity::getTargetId).toList(), applicableType);

        List<AllowancePolicyApplicableTargetDetailDTO> listDtos = new ArrayList<>();
        AllowancePolicyApplicableTargetDetailDTO dto;
        for (int i = 0; i < (listTargets.size()); i++) {
            dto = allowancePolicyApplicableTargetMapper.toGetAllDto(listEntity.get(i));
            dto.setTargetId(listTargets.get(i).getId());
            dto.setName(listTargets.get(i).getName());
            listDtos.add(dto);
        }

        return listDtos;
    }

    @Override
    public void delete(Long allowanceId) {
        List<AllowancePolicyApplicableTargetEntity> list = allowancePolicyApplicableTargetRepository.findByAllowancePolicyId(allowanceId);
        if (list.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        list.stream().iterator().forEachRemaining(allowancePolicyApplicableTargetRepository::delete);
    }

}
