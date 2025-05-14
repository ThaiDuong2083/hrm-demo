package com.example.apus_hrm_demo.mapper.allowance;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInAllowance.class)
public interface AllowanceMapper {
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToDto")
    @Mapping(source = "uomId", target = "uom", qualifiedByName = "uomToDTO")
    @Mapping(source = "currencyId", target = "currency", qualifiedByName = "currencyToDTO")
    @Mapping(source = "groupAllowanceId", target = "groupAllowance", qualifiedByName = "toGroupAllowanceDTO")
    AllowanceDTO toDto(AllowanceEntity allowanceEntity);


    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "groupAllowance.id",target = "groupAllowanceId")
    AllowanceEntity toEntity(AllowanceDTO allowanceFormDTO);

    @Mapping(source = "groupAllowanceId", target = "groupAllowance", qualifiedByName = "toGroupAllowanceDTO")
    AllowanceGetAllDTO toGetAllDto(AllowanceEntity allowanceEntity);

    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    void toUpdateEntity(AllowanceDTO allowanceDTO, @MappingTarget AllowanceEntity allowanceEntity);
}
