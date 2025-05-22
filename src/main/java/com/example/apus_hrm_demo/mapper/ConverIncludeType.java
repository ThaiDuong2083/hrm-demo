package com.example.apus_hrm_demo.mapper;

import com.example.apus_hrm_demo.util.enum_util.DeductionType;

import java.util.*;
import java.util.stream.Collectors;

public class ConverIncludeType {
    public String includeTypeToEntity(Set<DeductionType> includeType) {
        return includeType.stream().map(DeductionType::toString).collect(Collectors.joining(","));
    }

    public Set<DeductionType> includeTypeToDto (String includeType) {
        if (includeType == null || includeType.isBlank()) return new HashSet<>();
        return Arrays.stream(includeType.split(","))
                .map(str -> DeductionType.valueOf(str.trim()))
                .collect(Collectors.toSet());
    }

    public List<Set<DeductionType>> includeTypeToListDto(Set<String> includeTypes) {
        List<Set<DeductionType>> includeTypeLists = new ArrayList<>();
        if (includeTypes == null || includeTypes.isEmpty()) {return includeTypeLists;}
        includeTypes.forEach(str -> includeTypeLists.add(includeTypeToDto(str)));
        return includeTypeLists;
    }
}
