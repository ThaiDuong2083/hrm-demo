package com.example.apus_hrm_demo.mapper.allowance_policy;

import com.example.apus_hrm_demo.mapper.allowance_policy_line.AllowancePolicyLineMapper;
import com.example.apus_hrm_demo.repository.AllowancePolicyLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConvertInAllowancePolicy {
    private final AllowancePolicyLineRepository allowancePolicyLineRepository;
    private final AllowancePolicyLineMapper allowancePolicyLineMapper;

//    @Named("convertToAllowancePolicyLineDTO")
//    public List<AllowancePolicyLineGetAllDTO> convertToAllowancePolicyLineDTO(Long allowancePolicyId) {
//        GenericSpecificationBuilder<AllowancePolicyLineEntity> builder = new GenericSpecificationBuilder<>();
//        if (allowancePolicyId == null) {
//            return new ArrayList<>();
//        }
//        builder.with("allowancePolicyId", SearchOperation.EQUALITY, allowancePolicyId, false);
//        Specification<AllowancePolicyLineEntity> spec = builder.build();
//        List<AllowancePolicyLineEntity> list = allowancePolicyLineRepository.findAllByPayrollId(spec);
//        return list.stream().map(allowancePolicyLineMapper::toGetAllDto).toList();
//    }
}
