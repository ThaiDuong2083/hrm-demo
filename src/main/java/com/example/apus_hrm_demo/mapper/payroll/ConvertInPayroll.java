package com.example.apus_hrm_demo.mapper.payroll;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.entity.PayrollLineEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.DepartmentEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.EmployeeEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.PositionEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.payroll_line.PayrollLineContext;
import com.example.apus_hrm_demo.mapper.payroll_line.PayrollLineMapper;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDetailDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupAllowanceForPayrollDTO;
import com.example.apus_hrm_demo.model.payroll_line.group.GroupRewardForPayrollDTO;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import com.example.apus_hrm_demo.repository.PayrollLineRepository;
import com.example.apus_hrm_demo.service.PayrollLineService;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.PayrollLineType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConvertInPayroll {
    private final PayrollLineService payrollLineService;
    private final PayrollLineRepository payrollLineRepository;
    private final PayrollLineMapper payrollLineMapper;
    private final GroupRewardRepository groupRewardRepository;
    private final GroupAllowanceRepository groupAllowanceRepository;
    @Named("getEmployee")
    public BaseDTO getEmployee(Long employeeId) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/employee/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(employeeId, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<EmployeeEntity>>>() {});
    }

    @Named("getDepartment")
    public BaseDTO getDepartment(Long departmentId) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/department/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(departmentId, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<DepartmentEntity>>>() {});
    }

    @Named("getPosition")
    public BaseDTO getPosition(Long positionId) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/position/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(positionId, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<PositionEntity>>>() {});
    }

    @Named("getPayrollLines")
    public List<PayrollLineGetAllDTO> getPayrollLines(Long id) {
        return payrollLineService.findAll(id);
    }

    @Named("toGroupRewards")
    public List<GroupRewardForPayrollDTO> toGroupRewards(Long payrollId){
        List<GroupRewardForPayrollDTO> groupRewards = new ArrayList<>();
        List<String> allGroupName = new ArrayList<>();
        List<PayrollLineDetailDTO> payrollLines = new ArrayList<>();
        List<PayrollLineEntity> payrollLineEntities = payrollLineRepository.findByPayrollId(payrollId);
        for (PayrollLineEntity payrollLineEntity : payrollLineEntities){
            if (payrollLineEntity.getType() == PayrollLineType.REWARD){
                payrollLines.add(payrollLineMapper.toDto(payrollLineEntity,new PayrollLineContext(payrollLineEntity.getGroupTargetId(),payrollLineEntity.getType())));
                Optional<GroupRewardEntity> groupRewardEntityOptional =  groupRewardRepository.findById(payrollLineEntity.getGroupTargetId());
                if (groupRewardEntityOptional.isEmpty()){
                    throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
                }
                allGroupName.add(groupRewardEntityOptional.get().getName());
            }
        }
        groupRewards.add(new GroupRewardForPayrollDTO(allGroupName, payrollLines));
        return groupRewards;
    }

    @Named("toGroupAllowances")
    public List<GroupAllowanceForPayrollDTO> toGroupAllowances(Long payrollId){
        List<GroupAllowanceForPayrollDTO> groupAllowances = new ArrayList<>();
        List<String> allGroupName = new ArrayList<>();
        List<PayrollLineDetailDTO> payrollLines = new ArrayList<>();
        List<PayrollLineEntity> payrollLineEntities = payrollLineRepository.findByPayrollId(payrollId);
        for (PayrollLineEntity payrollLineEntity : payrollLineEntities){
            if (payrollLineEntity.getType() == PayrollLineType.ALLOWANCE){
                payrollLines.add(payrollLineMapper.toDto(payrollLineEntity,new PayrollLineContext(payrollLineEntity.getGroupTargetId(),payrollLineEntity.getType())));
                Optional<GroupAllowanceEntity> groupAllowanceEntityOptional =  groupAllowanceRepository.findById(payrollLineEntity.getGroupTargetId());
                if (groupAllowanceEntityOptional.isEmpty()){
                    throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
                }
                allGroupName.add(groupAllowanceEntityOptional.get().getName());
            }
        }
        groupAllowances.add(new GroupAllowanceForPayrollDTO(allGroupName, payrollLines));
        return groupAllowances;
    }
}
