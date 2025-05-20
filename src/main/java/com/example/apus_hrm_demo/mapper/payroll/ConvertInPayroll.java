package com.example.apus_hrm_demo.mapper.payroll;

import com.example.apus_hrm_demo.entity.call_other_sv.DepartmentEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.EmployeeEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.PositionEntity;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import com.example.apus_hrm_demo.service.PayrollLineService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertInPayroll {
    private final PayrollLineService payrollLineService;
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
}
