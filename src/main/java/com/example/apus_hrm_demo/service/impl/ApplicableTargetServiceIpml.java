package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.config.HearderCfg;
import com.example.apus_hrm_demo.entity.call_other_sv.BaseOtherSVEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.DepartmentEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.EmployeeEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.PositionEntity;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.service.ApplicableTargetService;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicableTargetServiceIpml implements ApplicableTargetService {
    private final RestTemplate rest = new RestTemplate();

    @Override
    public List<BaseOtherSVEntity> getApplicableTargets(List<Long> ids, ApplicableType applicableType) {
        if (applicableType == ApplicableType.ALL) {
            return null;
        }
        return getUrl(applicableType,ids);
    }

    private List<BaseOtherSVEntity> getUrl(ApplicableType applicableType, List<Long> ids) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1";
        switch (applicableType) {
            case ApplicableType.DEPARTMENT -> {
                return callApi(ids, url+"/department/list?page=0&size=20&ids=", new ParameterizedTypeReference<BaseResponse<ResponsePage<DepartmentEntity>>>() {});
            }
            case ApplicableType.POSITION -> {
                return callApi(ids, url+"/position/list?page=0&size=20&ids=", new ParameterizedTypeReference<BaseResponse<ResponsePage<PositionEntity>>>() {});
            }
            case ApplicableType.EMPLOYEE -> {
                return callApi(ids, url+"/employee/list?page=0&size=20&ids=", new ParameterizedTypeReference<BaseResponse<ResponsePage<EmployeeEntity>>>() {});
            }
            default -> throw new IllegalStateException("Unexpected value: " + applicableType);
        }
    }

    private <F extends BaseOtherSVEntity> List<BaseOtherSVEntity> callApi(List<Long> ids, String url, ParameterizedTypeReference<BaseResponse<ResponsePage<F>>> typeRef) {
        HttpHeaders headers = HearderCfg.getHeader();
        url = url+ids.toString().replace("[", "").replace("]", "").trim();
        HttpEntity<BaseResponse<ResponsePage<F>>> entity = new HttpEntity<>(headers);
        ResponseEntity<BaseResponse<ResponsePage<F>>> response = rest.exchange(url, HttpMethod.GET, entity, typeRef);
        BaseResponse<ResponsePage<F>> body = Objects.requireNonNull(response.getBody(), "Response body is null");

        List<F> items = Objects.requireNonNull(body.getData(), "Response data is null")
                .getContent();
        return new ArrayList<>(items);
    }
}
