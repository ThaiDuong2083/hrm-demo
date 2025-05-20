package com.example.apus_hrm_demo.mapper.base;

import org.mapstruct.MappingTarget;

public interface BaseMapper <E, D, A, C> {
    D toDto(E entity);

    C toDtoCu(E entity);
    E toEntity(D dto);
    A toGetAllDto(E entity);
    void toUpdateEntity(D dto, @MappingTarget E entity);
}
