package com.example.apus_hrm_demo.mapper.allowance_policy;

import com.example.apus_hrm_demo.entity.AllowancePolicyLineEntity;
import com.example.apus_hrm_demo.mapper.allowance_policy_line.AllowancePolicyLineMapper;
import com.example.apus_hrm_demo.model.allowance_policy_line.AllowancePolicyLineGetAllDTO;
import com.example.apus_hrm_demo.repository.AllowancePolicyLineRepository;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertInAllowancePolicy {
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final AllowancePolicyLineMapper allowancePolicyLineMapper;

    @Named("convertToAllowancePolicyLineDTO")
    public List<AllowancePolicyLineGetAllDTO> convertToAllowancePolicyLineDTO(Long allowancePolicyId) {
        GenericSpecificationBuilder<AllowancePolicyLineEntity> builder = new GenericSpecificationBuilder<>();
        if (allowancePolicyId == null) {
            return new ArrayList<>();
        }
        builder.with("allowancePolicyId", SearchOperation.EQUALITY, allowancePolicyId, false);
        Specification<AllowancePolicyLineEntity> spec = builder.build();
        List<AllowancePolicyLineEntity> list = allowancePolicyLineRepository.findAll(spec);
        return list.stream().map(allowancePolicyLineMapper::toGetAllDto).toList();
    }
}
