package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.service.AllowanceSercive;
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
public class AllowanceServiceImpl implements AllowanceSercive {
    private final AllowanceRepository allowanceRepository;
    private final BaseMapper<AllowanceEntity, AllowanceDTO,AllowanceGetAllDTO, ResponseAfterCUDTO> allowanceMapper;
    private final CommonResponseGenerator<AllowanceEntity, AllowanceDTO, AllowanceGetAllDTO, ResponseAfterCUDTO> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createAllowance(AllowanceDTO allowanceFormDTO) {
        AllowanceEntity allowanceEntity = allowanceMapper.toEntity(allowanceFormDTO);
        return commonResponseGenerator.returnCUResponse(allowanceRepository.save(allowanceEntity),
                                                        TraceIdGenarator.getTraceId(),
                                                        MessageResponseConstant.SUCCESS,
                                                        allowanceMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateAllowance(AllowanceDTO allowanceFormDTO) {
        Optional<AllowanceEntity> check = allowanceRepository.findById(allowanceFormDTO.getId());
        if (check.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND );
        }
        AllowanceEntity newAllowanceEntity = check.get();
        allowanceMapper.toUpdateEntity(allowanceFormDTO,newAllowanceEntity);

        return commonResponseGenerator.returnCUResponse(allowanceRepository.save(newAllowanceEntity),
                TraceIdGenarator.getTraceId(),
                MessageResponseConstant.SUCCESS,
                allowanceMapper);
    }

    @Override
    public BaseResponse<ResponsePage<AllowanceGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<AllowanceEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive !=null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }
        Specification<AllowanceEntity> spec = builder.build();
        Page<AllowanceEntity>  page = allowanceRepository.findAll(spec, pageable);
        return commonResponseGenerator.returnListResponse(page, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,allowanceMapper);
    }

    @Override
    public void deleteAllowance(Long id) {
        Optional<AllowanceEntity> check = allowanceRepository.findById(id);
        if (check.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND );
        }
        try {
            allowanceRepository.delete(check.get());
            allowanceRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE );
        }
    }

    @Override
    public BaseResponse<AllowanceDTO> findById(Long id) {
        Optional<AllowanceEntity> allowanceEntity = allowanceRepository.findById(id);
        if (allowanceEntity.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND );
        }
        return commonResponseGenerator.returnReadResponse(allowanceEntity.get(), TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,allowanceMapper);
    }


}
