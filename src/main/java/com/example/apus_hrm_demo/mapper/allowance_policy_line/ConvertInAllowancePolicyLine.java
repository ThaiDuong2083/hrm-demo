package com.example.apus_hrm_demo.mapper.allowance_policy_line;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.allowance.ConvertInAllowance;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.allowance.AllowanceForPolicyLineDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConvertInAllowancePolicyLine {
    private final AllowanceRepository allowanceRepository;
    private final ConvertInAllowance convertInAllowance;

    @Named("toAllowanceDto")
    public BaseDTO toAllowanceDto(Long allowanceId) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(allowanceId, allowanceRepository);
    }

    @Named("allowancePolicyLineDTO")
    public AllowanceForPolicyLineDTO allowancePolicyLineDTO(Long allowanceId) {
        if (allowanceId == null) {
            return null;
        }

        AllowanceEntity allowanceEntity = allowanceRepository.findById(allowanceId).orElseThrow(() ->
                new NullEntityException(
                        TraceIdGenarator.getTraceId(),
                        MessageResponseConstant.NOT_FOUND
                )
        );
        BaseDTO currency = convertInAllowance.currencyToDTO(allowanceEntity.getCurrencyId());
        BaseDTO uom = convertInAllowance.uomToDTO(allowanceEntity.getUomId());
        BaseDTO groupAllowance = convertInAllowance.toGroupAllowanceDTO(allowanceEntity.getGroupAllowanceId());
        Set<DeductionType> deductionTypeSet = convertInAllowance.deductionIncludeTypes(allowanceEntity.getIncludeType());

        return new AllowanceForPolicyLineDTO(allowanceEntity.getId(),
                allowanceEntity.getName(),
                allowanceEntity.getCode(),
                deductionTypeSet,
                groupAllowance,
                currency,
                uom,
                allowanceEntity.getType()
                );
    }
}
