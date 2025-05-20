package com.example.apus_hrm_demo.mapper.allowance_policy;

import com.example.apus_hrm_demo.entity.AllowancePolicyEntity;
import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyDetailDTO;
import com.example.apus_hrm_demo.model.allowance_policy.AllowancePolicyGetAllDto;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInAllowancePolicy.class)
public interface AllowancePolicyMapper extends BaseMapper<AllowancePolicyEntity, AllowancePolicyDetailDTO, AllowancePolicyGetAllDto, ResponseAfterCUDTO> {
    AllowancePolicyGetAllDto toGetAllDto(AllowancePolicyEntity entity);

    @Mapping(target = "target", ignore = true)
    @Mapping(source = "id", target = "allowancePolicyLine",qualifiedByName = "convertToAllowancePolicyLineDTO")
    AllowancePolicyDetailDTO toDto(AllowancePolicyEntity entity);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AllowancePolicyEntity toEntity(AllowancePolicyDTO dto);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toUpdateEntity(AllowancePolicyDTO dto, @MappingTarget AllowancePolicyEntity entity);
}
