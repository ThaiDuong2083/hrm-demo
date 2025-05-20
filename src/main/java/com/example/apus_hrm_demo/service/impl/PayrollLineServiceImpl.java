package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.PayrollLineEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.payroll_line.PayrollLineContext;
import com.example.apus_hrm_demo.mapper.payroll_line.PayrollLineMapper;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import com.example.apus_hrm_demo.repository.PayrollLineRepository;
import com.example.apus_hrm_demo.service.PayrollLineService;
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
public class PayrollLineServiceImpl implements PayrollLineService {
    private final PayrollLineRepository payrollLineRepository;
    private final PayrollLineMapper payrollLineMapper;

    @Override
    public void create(PayrollLineDTO line, Long payrollId) {
        PayrollLineEntity payrollLineEntity = payrollLineMapper.toEntity(line);
        payrollLineEntity.setPayrollId(payrollId);
        payrollLineRepository.save(payrollLineEntity);
    }

    @Override
    public void update(PayrollLineDTO line, Long payrollId) {
        PayrollLineEntity payrollLineEntity;
        if (line.getId() == null) {
            payrollLineEntity = payrollLineMapper.toEntity(line);
            payrollLineEntity.setPayrollId(payrollId);
        }else {
            Optional<PayrollLineEntity> optionalPayrollLineEntity = payrollLineRepository.findById(line.getId());
            if (optionalPayrollLineEntity.isEmpty()){
                throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
            }else {
                payrollLineEntity = optionalPayrollLineEntity.get();
                payrollLineMapper.toUpdateEntity(line, payrollLineEntity);
            }
        }
        payrollLineRepository.save(payrollLineEntity);
    }

    @Override
    public void deleteAll(Long payrollId) {
        List<PayrollLineEntity> list = payrollLineRepository.findByPayrollId(payrollId);
        if (!list.isEmpty()){
            list.stream().iterator().forEachRemaining(payrollLineRepository::delete);
        }
    }

    @Override
    public List<PayrollLineGetAllDTO> findAll(Long payrollId) {
        List<PayrollLineEntity> list = payrollLineRepository.findByPayrollId(payrollId);
        if (list.isEmpty()){
            return null;
        }
        return list.stream().map(entity->  payrollLineMapper.toGetAllDto(entity, new PayrollLineContext(entity.getGroupTargetId(),entity.getType()))).toList();
    }
}
