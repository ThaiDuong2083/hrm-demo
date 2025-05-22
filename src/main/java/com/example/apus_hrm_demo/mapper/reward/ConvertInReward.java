package com.example.apus_hrm_demo.mapper.reward;

import com.example.apus_hrm_demo.mapper.ConverIncludeType;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConvertInReward {
    private final ConverIncludeType converIncludeType = new ConverIncludeType();

    @Named("includeTypeToEntity")
    public String includeType(Set<DeductionType> includeType) {
        return converIncludeType.includeTypeToEntity(includeType);
    }

    @Named("includeTypeToDto")
    public Set<DeductionType> deductionIncludeTypes(String includeType) {
        if (includeType == null || includeType.isBlank()) return new HashSet<>();
        return converIncludeType.includeTypeToDto(includeType);
    }
}
