package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowancePolicyEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPolicyFilter;
import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.mapper.allowance_policy.AllowancePolicyMapper;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.AllowancePolicyRepository;
import com.example.apus_hrm_demo.service.AllowancePolicyApplicableTargetService;
import com.example.apus_hrm_demo.service.AllowancePolicyLineService;
import com.example.apus_hrm_demo.service.AllowancePolicyService;
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
public class AllowancePolicyServiceImpl implements AllowancePolicyService {
    private final AllowancePolicyRepository allowancePolicyRepository;
    private final AllowancePolicyMapper allowancePolicyMapper;
    private final AllowancePolicyLineService allowancePolicyLineService;
    private final CommonResponseGenerator<AllowancePolicyDTO, AllowancePolicyGetAllDto, AllowancePolicyEntity> commonResponseGenerator;
    private final AllowancePolicyApplicableTargetService allowancePolicyApplicableTargetService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(AllowancePolicyDTO allowancePolicyDTO) {
        AllowancePolicyEntity allowancePolicyEntity = allowancePolicyRepository.save(allowancePolicyMapper.toEntity(allowancePolicyDTO));

        allowancePolicyLineService.createOrUpdate(allowancePolicyDTO.getAllowancePolicyLine(), allowancePolicyEntity.getId());
        if (allowancePolicyDTO.getApplicableType() != ApplicableType.ALL) {
            allowancePolicyApplicableTargetService.createOrUpdate(allowancePolicyDTO.getTarget(), allowancePolicyEntity.getId());
        }
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyMapper.toDto(allowancePolicyEntity).getId());
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(AllowancePolicyDTO allowancePolicyDTO) {
        AllowancePolicyEntity allowancePolicyEntity = checkEntity(allowancePolicyDTO.getId());
        ApplicableType oldApplicableType = allowancePolicyEntity.getApplicableType();

        allowancePolicyMapper.toUpdateEntity(allowancePolicyDTO, allowancePolicyEntity);
        allowancePolicyRepository.save(allowancePolicyEntity);

        updateApplicableAndLine(allowancePolicyDTO, allowancePolicyEntity, oldApplicableType);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyMapper.toDto(allowancePolicyEntity).getId());
    }

    private AllowancePolicyEntity checkEntity(Long id){
        Optional<AllowancePolicyEntity> allowancePolicyEntityOptional = allowancePolicyRepository.findById(id);
        if (allowancePolicyEntityOptional.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return allowancePolicyEntityOptional.get();
    }

    private void updateApplicableAndLine(AllowancePolicyDTO allowancePolicyDTO, AllowancePolicyEntity allowancePolicyEntity, ApplicableType oldApplicableType) {
        if ( oldApplicableType != ApplicableType.ALL) {
            allowancePolicyApplicableTargetService.delete(allowancePolicyEntity.getId());
        }
        if (allowancePolicyDTO.getApplicableType() != ApplicableType.ALL) {
            allowancePolicyApplicableTargetService.createOrUpdate(allowancePolicyDTO.getTarget(), allowancePolicyEntity.getId());
        }
        allowancePolicyLineService.createOrUpdate(allowancePolicyDTO.getAllowancePolicyLine(), allowancePolicyEntity.getId());
    }

    @Override
    public BaseResponse<AllowancePolicyDTO> findById(Long allowanceId) {
        AllowancePolicyEntity allowancePolicyEntity = checkEntity(allowanceId);

        AllowancePolicyDTO allowancePolicyDTO = allowancePolicyMapper.toDto(allowancePolicyEntity);
        allowancePolicyDTO.setAllowancePolicyLine(allowancePolicyLineService.findByAllowancePolicyId(allowancePolicyEntity.getId()));
        allowancePolicyDTO.setTarget(allowancePolicyApplicableTargetService.findByAllowancePolicyId(allowancePolicyEntity.getId(), allowancePolicyEntity.getApplicableType()));

        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(),MessageResponseConstant.SUCCESS, allowancePolicyDTO);
    }

    @Override
    public BaseResponse<ResponsePage<AllowancePolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter) {
        GenericSpecificationBuilder<AllowancePolicyEntity> builder = CheckPolicyFilter.check(policyFliter);
        Specification<AllowancePolicyEntity> spec = builder.build();
        Page<AllowancePolicyEntity> page = allowancePolicyRepository.findAll(spec, pageable);
        List<AllowancePolicyGetAllDto> allowancePolicyDTOList = page.stream().map(allowancePolicyMapper::toGetAllDto).toList();
        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyDTOList,page);
    }

    @Override
    public void delete(Long id) {
        AllowancePolicyEntity allowancePolicyEntity = checkEntity(id);
        allowancePolicyLineService.deleteAll(allowancePolicyEntity.getId());
        allowancePolicyApplicableTargetService.delete(allowancePolicyEntity.getId());
        allowancePolicyRepository.delete(allowancePolicyEntity);
    }
}
