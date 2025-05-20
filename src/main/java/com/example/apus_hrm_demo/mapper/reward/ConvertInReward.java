package com.example.apus_hrm_demo.mapper.reward;

import com.example.apus_hrm_demo.entity.call_other_sv.CurrencyEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.UomEntity;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import com.example.apus_hrm_demo.util.enum_util.DeductionType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConvertInReward {
    private final GroupRewardRepository groupRewardRepository;

    @Named("includeTypeToEntity")
    public String includeType(Set<DeductionType> includeType) {
        return includeType.stream().map(DeductionType::toString).collect(Collectors.joining(","));
    }

    @Named("includeTypeToDto")
    public Set<DeductionType> deductionIncludeTypes(String includeType) {
        if (includeType == null || includeType.isBlank()) return new HashSet<>();
        return Arrays.stream(includeType.split(",")).map(str -> DeductionType.valueOf(str.trim())).collect(Collectors.toSet());
    }
    @Named("uomToDTO")
    public BaseDTO uomToDTO(Long uomId) {
        String url = "https://product-manufactor-service.dev.apusplatform.com/api/v1/unit/list?page=0&size=1&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
       return mapperNameCode.convertApi(uomId, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});
    }

    @Named("currencyToDTO")
    public BaseDTO currencyToDTO(Long currencyId) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/currency/list?page=0&size=20&currencyIds=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(currencyId, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<CurrencyEntity>>>() {});
    }

    @Named("toGroupRewardDTO")
    public BaseDTO toGroupRewardDTO(Long groupRewardId){
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(groupRewardId,groupRewardRepository);
    }
}
