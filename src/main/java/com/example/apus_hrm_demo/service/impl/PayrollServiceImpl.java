package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.PayrollEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.filter.CheckPayrollFilter;
import com.example.apus_hrm_demo.filter.PayrollFilter;
import com.example.apus_hrm_demo.mapper.payroll.PayrollMapper;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import com.example.apus_hrm_demo.repository.PayrollRepository;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.service.PayrollLineService;
import com.example.apus_hrm_demo.service.PayrollService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
    private final PayrollRepository payrollRepository;
    private final CommonResponseGenerator<PayrollDTO, PayrollGetAllDTO, PayrollEntity> commonResponseGenerator;
    private final PayrollMapper payrollMapper;
    private final PayrollLineService payrollLineService;
    private final ExtenalService extenalService;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(PayrollDTO dto) {
         PayrollEntity payrollEntity = payrollRepository.save(payrollMapper.toEntity(dto));
         payrollLineService.createOrUpdate(dto.getGroupAllowances(),dto.getGroupRewards(), payrollEntity.getId());
         return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollMapper.toDto(payrollEntity).getId());
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(PayrollDTO dto) {
        PayrollEntity payrollEntity = checkPayrollEntity(dto.getId());
        payrollMapper.toUpdateEntity(dto, payrollEntity);
        payrollLineService.createOrUpdate(dto.getGroupAllowances(),dto.getGroupAllowances(), payrollEntity.getId());

        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollMapper.toDto(payrollEntity).getId());
    }

    private PayrollEntity checkPayrollEntity(Long id) {
        Optional<PayrollEntity> payrollEntityOptional = payrollRepository.findById(id);
        if(payrollEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(),MessageResponseConstant.NOT_FOUND);
        }
        return payrollEntityOptional.get();
    }

    @Override
    public void delete(Long payrollId) {
        PayrollEntity payrollEntity = checkPayrollEntity(payrollId);
        payrollLineService.deleteAll(payrollId);
        payrollRepository.delete(payrollEntity);
    }

    @Override
    public BaseResponse<ResponsePage<PayrollGetAllDTO>> getAll(PayrollFilter payrollFilter, Pageable pageable) {
        GenericSpecificationBuilder<PayrollEntity> builder = CheckPayrollFilter.check(payrollFilter);
        Specification<PayrollEntity> spec = builder.build();
        Page<PayrollEntity> page = payrollRepository.findAll(spec, pageable);

        Map<Long,BaseDTO> employeeMaps = getEmployeeMaps(page.getContent().stream().map(PayrollEntity::getEmployeeId).collect(Collectors.toSet()));
        Map<Long,BaseDTO> positionMaps = getPositionMaps(page.getContent().stream().map(PayrollEntity::getPositionId).collect(Collectors.toSet()));

        List<PayrollGetAllDTO> payrollGetAllDTOS = page.getContent().stream().map(entity ->{
            PayrollGetAllDTO payrollGetAllDTO = payrollMapper.toGetAllDto(entity);
            payrollGetAllDTO.setPayrollLines(payrollLineService.findAllByPayrollId(entity.getId()));
            payrollGetAllDTO.setEmployee(employeeMaps.get(entity.getEmployeeId()));
            payrollGetAllDTO.setPosition(positionMaps.get(entity.getPositionId()));
            return payrollGetAllDTO;
        }).toList();

        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, payrollGetAllDTOS,page);
    }

    @Override
    public BaseResponse<PayrollDTO> findById(Long payrollId) {
        PayrollEntity payrollEntity = checkPayrollEntity(payrollId);
        PayrollDTO payrollDTO = payrollMapper.toDto(payrollEntity);

        payrollDTO.setDepartment(getDepartmentMaps(Set.of(payrollEntity.getDepartmentId())).get(payrollEntity.getDepartmentId()));
        payrollDTO.setPosition(getPositionMaps(Set.of(payrollEntity.getPositionId())).get(payrollEntity.getPositionId()));
        payrollDTO.setEmployee(getEmployeeMaps(Set.of(payrollEntity.getEmployeeId())).get(payrollEntity.getEmployeeId()));
        payrollDTO.setGroupAllowances(payrollLineService.findByPayrollId(payrollId,PayrollLineType.ALLOWANCE));
        payrollDTO.setGroupRewards(payrollLineService.findByPayrollId(payrollId,PayrollLineType.REWARD));
        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,payrollDTO);
    }

    private Map<Long, BaseDTO> getDepartmentMaps(Set<Long> departmentIds) {
        return extenalService.getDepartments(departmentIds).stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getPositionMaps(Set<Long> positionIds) {
        return extenalService.getPositions(positionIds).stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }

    private Map<Long, BaseDTO> getEmployeeMaps(Set<Long> employeeIds) {
        return extenalService.getEmployees(employeeIds).stream().collect(Collectors.toMap(BaseDTO::getId, Function.identity()));
    }
}
