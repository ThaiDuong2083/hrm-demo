package com.example.apus_hrm_demo.mapper.payroll;

import com.example.apus_hrm_demo.entity.PayrollEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollDetailDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInPayroll.class)
public interface PayrollMapper extends BaseMapper<PayrollEntity, PayrollDetailDTO,PayrollGetAllDTO, ResponseAfterCUDTO> {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "position.id", target = "positionId")
    PayrollEntity toEntity(PayrollDTO dto);

    @Mapping(source = "id",target = "groupRewards", qualifiedByName = "toGroupRewards")
    @Mapping(source = "id", target = "groupAllowances", qualifiedByName = "toGroupAllowances")
    @Mapping(source = "departmentId", target = "department", qualifiedByName = "getDepartment")
    @Mapping(source = "positionId", target = "position", qualifiedByName = "getPosition")
    @Mapping(source = "employeeId", target = "employee", qualifiedByName = "getEmployee")
    PayrollDetailDTO toDto(PayrollEntity entity);

    @Mapping(source = "positionId", target = "position", qualifiedByName = "getPosition")
    @Mapping(source = "employeeId", target = "employee", qualifiedByName = "getEmployee")
    @Mapping(source = "id", target = "payrollLines", qualifiedByName = "getPayrollLines")
    PayrollGetAllDTO toGetAllDto(PayrollEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "position.id", target = "positionId")
    void toUpdateEntity(PayrollDTO dto, @MappingTarget PayrollEntity entity);
}
