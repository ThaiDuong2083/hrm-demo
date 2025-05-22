package com.example.apus_hrm_demo.mapper.base;

import com.example.apus_hrm_demo.config.HearderCfg;
import com.example.apus_hrm_demo.entity.BaseEntity;
import com.example.apus_hrm_demo.entity.call_other_sv.BaseOtherSVEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.BaseRepository;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MapperNameCode {
    private final RestTemplate rest = new RestTemplate();
    private final HttpHeaders headers = HearderCfg.getHeader();
    public <E extends BaseEntity> BaseDTO convert(Long id, JpaRepository<E, Long> repository) {
        if (id == null) {
            return null;
        }

        E e = repository.findById(id).orElseThrow(() ->
                new NullEntityException(
                        TraceIdGenarator.getTraceId(),
                        MessageResponseConstant.NOT_FOUND
                )
        );

        BaseDTO dto = new BaseDTO();
        dto.setCode(e.getCode());
        dto.setName(e.getName());
        dto.setId(e.getId());
        return dto;
    }

    public <E extends BaseEntity> List<BaseDTO> convert(Set<Long> ids, BaseRepository<E> repository) {
        if (ids == null) {
            return new ArrayList<>();
        }

        List<E> e = repository.findByIds(ids);

        return e.stream().map(e1 -> {
            BaseDTO dto = new BaseDTO();
            dto.setCode(e1.getCode());
            dto.setName(e1.getName());
            dto.setId(e1.getId());
            return dto;
        }).toList();
    }

    public <F extends BaseOtherSVEntity> BaseDTO convertApi(Long id, String url, ParameterizedTypeReference<BaseResponse<ResponsePage<F>>> typeRef) {
        HttpEntity<BaseResponse<ResponsePage<F>>> entity = new HttpEntity<>(headers);
        ResponseEntity<BaseResponse<ResponsePage<F>>> response = rest.exchange(url+id,
                HttpMethod.GET, entity, typeRef);
        BaseResponse<ResponsePage<F>> body = Objects.requireNonNull(response.getBody(), "Response body is null");

        F item = Objects.requireNonNull(body.getData(), "Response data is null")
                .getContent()
                .getFirst();

        return new BaseDTO(item.getId(), item.getName(), item.getCode());
    }

    public <F extends BaseOtherSVEntity> List<BaseDTO> convertApi(Set<Long> ids, String url, ParameterizedTypeReference<BaseResponse<ResponsePage<F>>> typeRef) {
        var entity = new HttpEntity<BaseResponse<ResponsePage<F>>>(headers);
        ResponseEntity<BaseResponse<ResponsePage<F>>> response = rest.exchange(url+ids.toString().replace("[", "").replace("]", ""),
                HttpMethod.GET, entity, typeRef);
        BaseResponse<ResponsePage<F>> body = Objects.requireNonNull(response.getBody(), "Response body is null");

        List<F> items = Objects.requireNonNull(body.getData(), "Response data is null")
                .getContent();
        return items.stream()
                .map(item -> new BaseDTO(item.getId(), item.getName(), item.getCode()))
                .toList();
    }
}
