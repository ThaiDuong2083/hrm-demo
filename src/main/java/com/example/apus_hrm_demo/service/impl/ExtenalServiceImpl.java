package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.call_other_sv.CurrencyEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.UomEntity;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.service.ExtenalService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ExtenalServiceImpl implements ExtenalService {

    @Override
    public List<BaseDTO> getCurrency(Set<Long> currencyIds) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/currency/list?page=0&size=20&currencyIds=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(currencyIds, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<CurrencyEntity>>>() {});
    }

    @Override
    public List<BaseDTO> getUom(Set<Long> uomIds) {
        String url = "https://product-manufactor-service.dev.apusplatform.com/api/v1/unit/list?page=0&size=1&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(uomIds, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});
    }

    @Override
    public List<BaseDTO> getEmployees(Set<Long> employeeIds) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/employee/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(employeeIds, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});
    }

    @Override
    public List<BaseDTO> getPositions(Set<Long> positionIds) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/position/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(positionIds, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});
    }

    @Override
    public List<BaseDTO> getDepartments(Set<Long> departmentIds) {
        String url = "https://resources-service.dev.apusplatform.com/api/v1/department/list?page=0&size=20&ids=";
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convertApi(departmentIds, url, new ParameterizedTypeReference<BaseResponse<ResponsePage<UomEntity>>>() {});
    }
}
