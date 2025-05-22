package com.example.apus_hrm_demo.mapper.payroll;

import com.example.apus_hrm_demo.entity.PayrollEntity;
import com.example.apus_hrm_demo.model.payroll.PayrollDTO;
import com.example.apus_hrm_demo.model.payroll.PayrollGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInPayroll.class)
public interface PayrollMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "position.id", target = "positionId")
    PayrollEntity toEntity(PayrollDTO dto);

    @Mapping(target = "groupRewards", ignore = true)
    @Mapping(target = "groupAllowances", ignore = true)
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(source = "employeeId", target = "employee.id")
    PayrollDTO toDto(PayrollEntity entity);

    @Mapping(target = "payrollLines", ignore = true)
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(source = "employeeId", target = "employee.id")
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
