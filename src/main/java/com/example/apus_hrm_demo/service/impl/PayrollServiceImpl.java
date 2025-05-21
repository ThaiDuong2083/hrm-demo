package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.PayrollEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPayrollFilter;
import com.example.apus_hrm_demo.filter.PayrollFilter;
import com.example.apus_hrm_demo.mapper.payroll.PayrollMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollDetailDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.repository.PayrollRepository;
import com.example.apus_hrm_demo.service.PayrollLineService;
import com.example.apus_hrm_demo.service.PayrollService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;
    private final CommonResponseGenerator<PayrollEntity, PayrollDetailDTO, PayrollGetAllDTO, ResponseAfterCUDTO> commonResponseGenerator;
    private final PayrollMapper payrollMapper;
    private final PayrollLineService payrollLineService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(PayrollDTO dto) {
         PayrollEntity payrollEntity = payrollRepository.save(payrollMapper.toEntity(dto));
         for(PayrollLineDTO payrollLineDTO : dto.getPayrollLines()){
             payrollLineService.create(payrollLineDTO, payrollEntity.getId());
         }
         return commonResponseGenerator.returnCUResponse(payrollEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(PayrollDTO dto) {
        Optional<PayrollEntity> payrollEntityOptional = payrollRepository.findById(dto.getId());
        if(payrollEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(),MessageResponseConstant.NOT_FOUND);
        }
        PayrollEntity payrollEntity = payrollEntityOptional.get();
        payrollMapper.toUpdateEntity(dto, payrollEntity);
        for(PayrollLineDTO payrollLineDTO : dto.getPayrollLines()){
            payrollLineService.create(payrollLineDTO, payrollEntity.getId());
        }
        return commonResponseGenerator.returnCUResponse(payrollEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollMapper);
    }

    @Override
    public void delete(Long payrollId) {
        Optional<PayrollEntity> payrollEntityOptional = payrollRepository.findById(payrollId);
        if(payrollEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(),MessageResponseConstant.NOT_FOUND);
        }
        PayrollEntity payrollEntity = payrollEntityOptional.get();
        payrollLineService.deleteAll(payrollId);
        payrollRepository.delete(payrollEntity);
    }

    @Override
    public BaseResponse<ResponsePage<PayrollGetAllDTO>> getAll(PayrollFilter payrollFilter, Pageable pageable) {
        GenericSpecificationBuilder<PayrollEntity> builder = CheckPayrollFilter.check(payrollFilter);
        Specification<PayrollEntity> spec = builder.build();
        Page<PayrollEntity> page = payrollRepository.findAll(spec, pageable);
        return commonResponseGenerator.returnListResponse(page, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, payrollMapper);
    }

    @Override
    public BaseResponse<PayrollDetailDTO> findById(Long payrollId) {
        Optional<PayrollEntity> payrollEntityOptional = payrollRepository.findById(payrollId);
        if(payrollEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(),MessageResponseConstant.NOT_FOUND);
        }
        return commonResponseGenerator.returnReadResponse(payrollEntityOptional.get(), TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollMapper);
    }
}
