package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowancePolicyLineEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.allowance_policy_line.AllowancePolicyLineMapper;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineGetAllDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyLineRepository;
import com.example.apus_hrm_demo.service.AllowancePolicyLineService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AllowancePolicyLineServiceImpl implements AllowancePolicyLineService {
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final AllowancePolicyLineMapper allowancePolicyLineMapper;

    @Override
    public void create(AllowancePolicyLineDTO allowancePolicyLineDTO, Long allowancePolicyId) {
            AllowancePolicyLineEntity allowancePolicyLineEntity = allowancePolicyLineMapper.toEntity(allowancePolicyLineDTO);
            allowancePolicyLineEntity.setAllowancePolicyId(allowancePolicyId);
            allowancePolicyLineRepository.save(allowancePolicyLineEntity);
    }

    @Override
    public void update(AllowancePolicyLineDTO allowancePolicyLineDTO, Long allowancePolicyId) {
        if (allowancePolicyLineDTO.getId() == null) {
            create(allowancePolicyLineDTO, allowancePolicyId);
        } else {
            Optional<AllowancePolicyLineEntity> allowancePolicyLineEntityOptional = allowancePolicyLineRepository.findById(allowancePolicyLineDTO.getId());
            if (allowancePolicyLineEntityOptional.isEmpty()) {
                throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
            }
            AllowancePolicyLineEntity allowancePolicyLineEntity = allowancePolicyLineEntityOptional.get();
            allowancePolicyLineMapper.toUpdateEntity(allowancePolicyLineDTO, allowancePolicyLineEntity);
            allowancePolicyLineRepository.save(allowancePolicyLineEntity);
        }
    }
    @Override
    public List<AllowancePolicyLineGetAllDTO> findByAllowancePolicyId(Long allowancePolicyId) {
        List<AllowancePolicyLineEntity> list = allowancePolicyLineRepository.findByAllowancePolicyId(allowancePolicyId);
        return list.stream().map(allowancePolicyLineMapper::toGetAllDto).toList();
    }

    @Override
    public void deleteAll(Long allowancePolicyId) {
        List<AllowancePolicyLineEntity> list = allowancePolicyLineRepository.findByAllowancePolicyId(allowancePolicyId);
        if (!list.isEmpty()){
            list.stream().iterator().forEachRemaining(allowancePolicyLineRepository::delete);
        }
    }

    @Override
    public void deleteById(List<Long> lineIds, Long allowancePolicyId) {
        List<Long> lineEntityIds = allowancePolicyLineRepository.findAllIdByAllowancePolicy(allowancePolicyId);
        if (lineEntityIds.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        for (Long lineEntityId : lineEntityIds){
            if (!lineIds.contains(lineEntityId)){
                allowancePolicyLineRepository.deleteById(lineEntityId);
            }
        }
    }
}

