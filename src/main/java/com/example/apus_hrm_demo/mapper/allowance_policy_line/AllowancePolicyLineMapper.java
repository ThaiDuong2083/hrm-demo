package com.example.apus_hrm_demo.mapper.allowance_policy_line;

import com.example.apus_hrm_demo.entity.AllowancePolicyLineEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineDTO;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineGetAllDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInAllowancePolicyLine.class)
public interface AllowancePolicyLineMapper extends BaseMapper<AllowancePolicyLineEntity, AllowancePolicyLineDTO, AllowancePolicyLineGetAllDTO, ResponseAfterCUDTO> {

    @Mapping(source = "allowanceId", target = "allowance", qualifiedByName = "toAllowanceDto")
    AllowancePolicyLineDTO toDto(AllowancePolicyLineEntity entity);

    @Mapping(target = "allowanceId", source = "allowance.id")
    @Mapping(target = "allowancePolicyId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    AllowancePolicyLineEntity toEntity(AllowancePolicyLineDTO dto);

    @Mapping(target = "allowanceId", source = "allowance.id")
    @Mapping(target = "allowancePolicyId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(AllowancePolicyLineDTO dto, @MappingTarget AllowancePolicyLineEntity entity);

    @Mapping(target = "allowance", source = "allowanceId", qualifiedByName = "allowancePolicyLineDTO")
    AllowancePolicyLineGetAllDTO toGetAllDto(AllowancePolicyLineEntity entity);
}
