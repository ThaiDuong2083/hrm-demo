package com.example.apus_hrm_demo.mapper.payroll_line;

import com.example.apus_hrm_demo.entity.PayrollLineEntity;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInPayrollLine.class)
public interface PayrollLineMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(source = "line.group.id",target = "groupTargetId")
    @Mapping(source = "line.id", target = "targetId")
    @Mapping(target = "payrollId", ignore = true)
    PayrollLineEntity toEntity(PayrollLineDTO dto);

    @Mapping(source = "targetId",target = "line.id")
    PayrollLineDTO toDto(PayrollLineEntity entity);

    @Mapping(target = "targetName", ignore = true)
    @Mapping(target = "cycle", ignore = true)
    PayrollLineGetAllDTO toGetAllDto(PayrollLineEntity entity);

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "payrollId", ignore = true)
    @Mapping(source = "line.id", target = "targetId")
    @Mapping(source = "line.group.id", target = "groupTargetId")
    void toUpdateEntity(PayrollLineDTO dto,@MappingTarget PayrollLineEntity entity);
}
