package com.example.apus_hrm_demo.mapper.payroll_line;

import com.example.apus_hrm_demo.entity.PayrollLineEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineDetailDTO;
import com.example.apus_hrm_demo.model.payroll_line.PayrollLineGetAllDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInPayrollLine.class)
public interface PayrollLineMapper extends BaseMapper<PayrollLineEntity, PayrollLineDetailDTO, PayrollLineGetAllDTO, ResponseAfterCUDTO> {

    @Mapping(source = "groupTarget.id",target = "groupTargetId")
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(target = "payrollId", ignore = true)
    PayrollLineEntity toEntity(PayrollLineDTO dto);

    @Mapping(source = "targetId",target = "line", qualifiedByName = "toLineDTO")
    PayrollLineDetailDTO toDto(PayrollLineEntity entity, @Context PayrollLineContext payrollLineContext);

    @Mapping(source = "targetId", target = "targetName" ,qualifiedByName = "getTargetName")
    @Mapping(source = "targetId", target = "cycle", qualifiedByName = "getCycleOfTarget")
    PayrollLineGetAllDTO toGetAllDto(PayrollLineEntity entity, @Context PayrollLineContext payrollLineContext);

    @Mapping(target = "payrollId", ignore = true)
    @Mapping(source = "target.id", target = "targetId")
    @Mapping(source = "groupTarget.id", target = "groupTargetId")
    void toUpdateEntity(PayrollLineDTO dto,@MappingTarget PayrollLineEntity entity);
}
