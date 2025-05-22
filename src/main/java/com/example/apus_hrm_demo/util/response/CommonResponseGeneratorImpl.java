package com.example.apus_hrm_demo.util.response;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.util.i18n.GenerateMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonResponseGeneratorImpl<D, L, E> implements CommonResponseGenerator<D,L, E> {

    private final GenerateMessage generateMessage;
    private final HttpServletRequest request;

    public BaseResponse<ResponseAfterCUDTO> returnCUResponse(String traceId, String code, Long id) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        response.setData(new ResponseAfterCUDTO(id));
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }

    public BaseResponse<D> returnReadResponse( String traceId, String code, D dto) {
        BaseResponse<D> response = new BaseResponse<>();
        response.setData(dto);
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }

    public BaseResponse<ResponsePage<L>> returnListResponse(String traceId, String code, List<L> list, Page<E> page) {
        ResponsePage<L> responsePage = new ResponsePage<>(
                list,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSort().toString(),
                page.getNumberOfElements()
        );

        BaseResponse<ResponsePage<L>> response = new BaseResponse<>();
        response.setData(responsePage);
        response.setTraceId(traceId);
        response.setMessage(generateMessage.getMessage(code, request.getLocale()));
        return response;
    }
}
