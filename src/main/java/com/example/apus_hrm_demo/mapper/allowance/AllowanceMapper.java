package com.example.apus_hrm_demo.mapper.allowance;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInAllowance.class)
public interface AllowanceMapper {
    @Mapping(target = "uom.id", source = "uomId")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToDto")
    @Mapping(source = "groupAllowanceId", target = "groupAllowance.id")
    AllowanceDTO toDto(AllowanceEntity allowanceEntity);

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "groupAllowance.id",target = "groupAllowanceId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    AllowanceEntity toEntity(AllowanceDTO allowanceFormDTO);

    @Mapping(source = "groupAllowanceId", target = "groupAllowance.id")
    AllowanceGetAllDTO toGetAllDto(AllowanceEntity allowanceEntity);

    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "groupAllowance.id", target = "groupAllowanceId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(AllowanceDTO allowanceDTO, @MappingTarget AllowanceEntity allowanceEntity);
}
