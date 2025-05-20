package com.example.apus_hrm_demo.util.response;

import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.util.i18n.GenerateMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonResponseGeneratorImpl<E, D, B,C> implements CommonResponseGenerator <E,D,B,C> {

    private final GenerateMessage generateMessage;
    private final HttpServletRequest request;

    @Override
    public BaseResponse<C> returnCUResponse(E entity, String traceId, String code, BaseMapper<E, D, B,C> mapper) {
        BaseResponse<C> response = new BaseResponse<>();
        response.setData(mapper.toDtoCu(entity));
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }

    @Override
    public BaseResponse<D> returnReadResponse(E entity, String traceId, String code, BaseMapper<E, D, B,C> mapper) {
        BaseResponse<D> response = new BaseResponse<>();
        response.setData(mapper.toDto(entity));
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<B>> returnListResponse(Page<E> page, String traceId, String code, BaseMapper<E, D, B,C> mapper) {
        ResponsePage<B> responsePage = new ResponsePage<>(
                page.getContent().stream().map(mapper::toGetAllDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSort().toString(),
                page.getNumberOfElements()
        );

        BaseResponse<ResponsePage<B>> response = new BaseResponse<>();
        response.setData(responsePage);
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }
}
