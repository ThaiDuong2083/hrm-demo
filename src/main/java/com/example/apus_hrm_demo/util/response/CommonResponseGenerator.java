package com.example.apus_hrm_demo.util.response;

import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommonResponseGenerator<D,L,E> {
    BaseResponse<D> returnReadResponse( String traceId, String code, D dto);
    BaseResponse<ResponseAfterCUDTO> returnCUResponse(String traceId, String code, Long id);
    BaseResponse<ResponsePage<L>> returnListResponse(String traceId, String code, List<L> list, Page<E> page);
}
