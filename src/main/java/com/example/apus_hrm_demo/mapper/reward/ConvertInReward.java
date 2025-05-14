package com.example.apus_hrm_demo.mapper.reward;

import com.example.apus_hrm_demo.entity.CurrencyEntity;
import com.example.apus_hrm_demo.entity.UomEntity;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import com.example.apus_hrm_demo.util.DeductionType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConvertInReward {
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final RestTemplate rest = new RestTemplate();

    @Named("includeTypeToEntity")
    public String includeType(Set<DeductionType> includeType) {
        return includeType.stream().map(DeductionType::toString).collect(Collectors.joining(","));
    }

    @Named("includeTypeToDto")
    public Set<DeductionType> deductionIncludeTypes(String includeType) {
        if (includeType == null || includeType.isBlank()) return null;
        return Arrays.stream(includeType.split(",")).map(str ->{
            return DeductionType.valueOf(str.trim());
        }).collect(Collectors.toSet());
    }
    @Named("uomToDTO")
    public BaseDTO uomToDTO(Long uomId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.set("Authorization", "Bearer eyJraWQiOiJkZWY3ZDIxZi1lMTY5LTRiYzEtODYyMi05NDY0OTIxZWU1ZTAiLCJhbGciOiJSUzI1NiJ9.eyJ0ZW5hbnRfaWQiOiIyMDEiLCJzdWIiOiJhcG9kaW9AZ21haWwuY29tIiwiaXNfb3duZXIiOnRydWUsImlzcyI6Imh0dHA6Ly8xMC4wLjEuOTY6ODA4MSIsImF1dGhvcml0aWVzIjpbIk1hbnVmYWN0b3J5IiwiQkxETk0iXSwiYXVkIjoidGVzdCIsIm5iZiI6MTc0NzEwNzI4NywidXNlcl9pZCI6NTgsIm9yZ19pZCI6MSwic2NvcGUiOlsiZ2F0ZXdheSJdLCJleHAiOjE3NDc5NzEyODcsImlhdCI6MTc0NzEwNzI4NywianRpIjoiYTE0ZDk5ZjMtZDE4MC00NzI3LTg5NDQtOWQ3ZDJhNDgyMTJjIn0.Z13K01YTQMn92enty4z5PQjW7HDBHsHobsaRRv2faCJfk8qRHKah8YITzaJJfUBdRFhEZQFfuZppu6PnyqW0roMS_5ZXiEvmkil-NL17V96lQ-P5jTNJgY2HYmUHT5UDXPn_CM2KhyoicnVyG7Eicdjavk0BPZlMu87r5ry5sbfA6tfxQMgNUoNDr-d-Q7XBQj26bZDuQ64qa0Nblumfq3hlXhMFBipoNaeICpNX3qtMsj9Z_A1Hgn7VWBEUu5tYME0jfbPwbGJyD4n1EThfo_RNpnaV-0gKsql9_sd7_d-hB1OP0LYmCmNJvTk2IjTBBI4WdgQL1egpMfN-i2AJ2A");
        headers.set("X-TenantId", "201");
        String url = "https://product-manufactor-service.dev.apusplatform.com/api/v1/unit/list?page=0&size=1&ids="+uomId;

        HttpEntity<BaseResponse<ResponsePage<UomEntity>>> entity = new HttpEntity<>(headers);
        ResponseEntity<BaseResponse<ResponsePage<UomEntity>>> response = rest.exchange(url,
                HttpMethod.GET, entity,  new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});

        return new BaseDTO(response.getBody().getData().getContent().getFirst().getId(),
                response.getBody().getData().getContent().getFirst().getName(),
                response.getBody().getData().getContent().getFirst().getCode());
    }

    @Named("currencyToDTO")
    public BaseDTO currencyToDTO(Long currencyId) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/currency/list?page=0&size=20&currencyIds="+currencyId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.set("Authorization", "Bearer eyJraWQiOiJkZWY3ZDIxZi1lMTY5LTRiYzEtODYyMi05NDY0OTIxZWU1ZTAiLCJhbGciOiJSUzI1NiJ9.eyJ0ZW5hbnRfaWQiOiIyMDEiLCJzdWIiOiJhcG9kaW9AZ21haWwuY29tIiwiaXNfb3duZXIiOnRydWUsImlzcyI6Imh0dHA6Ly8xMC4wLjEuOTY6ODA4MSIsImF1dGhvcml0aWVzIjpbIk1hbnVmYWN0b3J5IiwiQkxETk0iXSwiYXVkIjoidGVzdCIsIm5iZiI6MTc0NzEwNzI4NywidXNlcl9pZCI6NTgsIm9yZ19pZCI6MSwic2NvcGUiOlsiZ2F0ZXdheSJdLCJleHAiOjE3NDc5NzEyODcsImlhdCI6MTc0NzEwNzI4NywianRpIjoiYTE0ZDk5ZjMtZDE4MC00NzI3LTg5NDQtOWQ3ZDJhNDgyMTJjIn0.Z13K01YTQMn92enty4z5PQjW7HDBHsHobsaRRv2faCJfk8qRHKah8YITzaJJfUBdRFhEZQFfuZppu6PnyqW0roMS_5ZXiEvmkil-NL17V96lQ-P5jTNJgY2HYmUHT5UDXPn_CM2KhyoicnVyG7Eicdjavk0BPZlMu87r5ry5sbfA6tfxQMgNUoNDr-d-Q7XBQj26bZDuQ64qa0Nblumfq3hlXhMFBipoNaeICpNX3qtMsj9Z_A1Hgn7VWBEUu5tYME0jfbPwbGJyD4n1EThfo_RNpnaV-0gKsql9_sd7_d-hB1OP0LYmCmNJvTk2IjTBBI4WdgQL1egpMfN-i2AJ2A");

        HttpEntity<BaseResponse<ResponsePage<CurrencyEntity>>> entity = new HttpEntity<>(headers);
        ResponseEntity<BaseResponse<ResponsePage<CurrencyEntity>>> response = rest.exchange(url,
                HttpMethod.GET, entity,  new ParameterizedTypeReference<BaseResponse<ResponsePage<CurrencyEntity>>>() {});

        return new BaseDTO(response.getBody().getData().getContent().getFirst().getId(),
                response.getBody().getData().getContent().getFirst().getName(),
                null);
    }
}
