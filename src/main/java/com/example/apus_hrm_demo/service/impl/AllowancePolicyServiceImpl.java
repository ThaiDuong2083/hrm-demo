package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowancePolicyEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPolicyFilter;
import com.example.apus_hrm_demo.filter.PolicyFliter;
import com.example.apus_hrm_demo.mapper.allowance_policy.AllowancePolicyMapper;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDetailDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
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
import com.example.apus_hrm_demo.util.i18n.GenerateMessage;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
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
    private final CommonResponseGenerator<AllowancePolicyEntity, AllowancePolicyDetailDTO, AllowancePolicyGetAllDto, ResponseAfterCUDTO> commonResponseGenerator;
    private final HttpServletRequest request;
    private final GenerateMessage generateMessage;
    private final AllowancePolicyApplicableTargetService allowancePolicyApplicableTargetService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(AllowancePolicyDTO allowancePolicyDTO) {
        AllowancePolicyEntity allowancePolicyEntity = allowancePolicyRepository.save(allowancePolicyMapper.toEntity(allowancePolicyDTO));
        int length = (allowancePolicyDTO.getAllowancePolicyLine().size()<= allowancePolicyDTO.getTarget().size()) ? allowancePolicyDTO.getTarget().size()-1 : allowancePolicyDTO.getAllowancePolicyLine().size()-1;

        for(int i=0;i<=length;i++){
            if(i < allowancePolicyDTO.getAllowancePolicyLine().size()){
                allowancePolicyLineService.create(allowancePolicyDTO.getAllowancePolicyLine().get(i),allowancePolicyEntity.getId());
            }
            if(i < allowancePolicyDTO.getTarget().size() && allowancePolicyDTO.getApplicableType()!= ApplicableType.ALL){
                allowancePolicyApplicableTargetService.create(allowancePolicyDTO.getTarget().get(i),allowancePolicyEntity.getId());
            }
        }
        return commonResponseGenerator.returnCUResponse(allowancePolicyEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(AllowancePolicyDTO allowancePolicyDTO) {
        Optional<AllowancePolicyEntity> allowancePolicyEntityOptional = allowancePolicyRepository.findById(allowancePolicyDTO.getId());
        if (allowancePolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        AllowancePolicyEntity allowancePolicyEntity = allowancePolicyEntityOptional.get();
        Boolean checkApplicable = (allowancePolicyEntity.getApplicableType() == allowancePolicyDTO.getApplicableType());
        ApplicableType oldApplicableType = allowancePolicyEntity.getApplicableType();

        allowancePolicyMapper.toUpdateEntity(allowancePolicyDTO, allowancePolicyEntity);
        allowancePolicyRepository.save(allowancePolicyEntity);

        updateApplicable(allowancePolicyDTO,allowancePolicyEntity, checkApplicable, oldApplicableType);
        return commonResponseGenerator.returnCUResponse(allowancePolicyEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyMapper);
    }

    private void updateApplicable(AllowancePolicyDTO allowancePolicyDTO, AllowancePolicyEntity allowancePolicyEntity, Boolean checkApplicable, ApplicableType oldApplicableType) {
        int length = (allowancePolicyDTO.getAllowancePolicyLine().size()>= allowancePolicyDTO.getTarget().size()) ? allowancePolicyDTO.getAllowancePolicyLine().size()-1 : allowancePolicyDTO.getTarget().size()-1;
        if(Boolean.FALSE.equals(checkApplicable) && oldApplicableType!= ApplicableType.ALL) {
            allowancePolicyApplicableTargetService.delete(allowancePolicyEntity.getId());
        }
        for (int i = 0; i <= length; i++) {
            if (i < allowancePolicyDTO.getAllowancePolicyLine().size()) {
                allowancePolicyLineService.update(allowancePolicyDTO.getAllowancePolicyLine().get(i), allowancePolicyEntity.getId());
            }
            if ((i < allowancePolicyDTO.getTarget().size()) && (allowancePolicyDTO.getApplicableType() != ApplicableType.ALL)) {
                if (allowancePolicyDTO.getApplicableType()!= allowancePolicyEntity.getApplicableType()) {
                    allowancePolicyApplicableTargetService.create(allowancePolicyDTO.getTarget().get(i), allowancePolicyEntity.getId());
                }else {
                    allowancePolicyApplicableTargetService.update(allowancePolicyDTO.getTarget().get(i), allowancePolicyEntity.getId());
                }
            }
        }
        List<Long> lineIds = allowancePolicyDTO.getAllowancePolicyLine().stream().map(AllowancePolicyLineDTO::getId).toList();
        allowancePolicyLineService.deleteById(lineIds,allowancePolicyEntity.getId());
    }

    @Override
    public BaseResponse<AllowancePolicyDetailDTO> findById(Long allowanceId) {
        Optional<AllowancePolicyEntity> allowancePolicyEntityOptional = allowancePolicyRepository.findById(allowanceId);

        if (allowancePolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }

        AllowancePolicyEntity allowancePolicyEntity = allowancePolicyEntityOptional.get();

        AllowancePolicyDetailDTO allowancePolicyDetailDTO = allowancePolicyMapper.toDto(allowancePolicyEntity);
        allowancePolicyDetailDTO.setAllowancePolicyLine(allowancePolicyLineService.findByAllowancePolicyId(allowancePolicyEntity.getId()));
        allowancePolicyDetailDTO.setTarget(allowancePolicyApplicableTargetService.findByAllowancePolicyId(allowancePolicyEntity.getId(), allowancePolicyEntity.getApplicableType()));

        BaseResponse<AllowancePolicyDetailDTO> response = new BaseResponse<>();
        response.setData(allowancePolicyDetailDTO);
        response.setTraceId(TraceIdGenarator.getTraceId());
        response.setMessage(generateMessage.getMessage(MessageResponseConstant.SUCCESS, request.getLocale()));
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<AllowancePolicyGetAllDto>> findAll(Pageable pageable, PolicyFliter policyFliter) {
        GenericSpecificationBuilder<AllowancePolicyEntity> builder = CheckPolicyFilter.check(policyFliter);
        Specification<AllowancePolicyEntity> spec = builder.build();
        Page<AllowancePolicyEntity> allowancePolicyEntities = allowancePolicyRepository.findAll(spec,pageable);
        return commonResponseGenerator.returnListResponse(allowancePolicyEntities,TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowancePolicyMapper);
    }

    @Override
    public void delete(Long id) {
        Optional<AllowancePolicyEntity> allowancePolicyEntityOptional = allowancePolicyRepository.findById(id);
        if (allowancePolicyEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        AllowancePolicyEntity allowancePolicyEntity = allowancePolicyEntityOptional.get();
        allowancePolicyLineService.deleteAll(allowancePolicyEntity.getId());
        allowancePolicyApplicableTargetService.delete(allowancePolicyEntity.getId());
        allowancePolicyRepository.delete(allowancePolicyEntity);
    }
}
