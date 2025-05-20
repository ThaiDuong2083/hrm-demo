package com.example.apus_hrm_demo.util.response;

import com.example.apus_hrm_demo.mapper.base.BaseMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import org.springframework.data.domain.Page;

public interface CommonResponseGenerator <E, D, A, C> {
    BaseResponse<D> returnReadResponse(E entity, String traceId, String code, BaseMapper<E, D, A,C> mapper);
    BaseResponse<C> returnCUResponse(E entity, String traceId, String code, BaseMapper<E, D, A,C> mapper);
    BaseResponse<ResponsePage<A>> returnListResponse(Page<E> page, String traceId, String code, BaseMapper<E, D, A,C> mapper);
}
