package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.mapper.GenericReponseAfterCUMapper;
import com.example.apus_hrm_demo.mapper.allowance.AllowanceMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.service.AllowanceSercive;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.speficiation.SearchOperation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceSercive {
    private final AllowanceRepository allowanceRepository;
    private final AllowanceMapper allowanceMapper;
    private final GenericReponseAfterCUMapper<AllowanceEntity> genericReponseAfterCUMapper;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createAllowance(AllowanceDTO allowanceFormDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<Long> check = allowanceRepository.checkExistId(allowanceFormDTO.getId());
        if (check.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + allowanceFormDTO.getId() + " does not exist");
            return response;
        }

        AllowanceEntity allowanceEntity = allowanceMapper.toEntity(allowanceFormDTO);
        response.setData(genericReponseAfterCUMapper.toDto(allowanceRepository.save(allowanceEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateAllowance(AllowanceDTO allowanceFormDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<AllowanceEntity> check = allowanceRepository.findById(allowanceFormDTO.getId());
        if (check.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + allowanceFormDTO.getId() + " does not exist");
            return response;
        }

        AllowanceEntity newAllowanceEntity = check.get();
        allowanceMapper.toUpdateEntity(allowanceFormDTO,newAllowanceEntity);

        response.setData(genericReponseAfterCUMapper.toDto(allowanceRepository.save(newAllowanceEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<AllowanceGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        BaseResponse<ResponsePage<AllowanceGetAllDTO>> response = new BaseResponse<>();
        ResponsePage<AllowanceGetAllDTO> responsePage = new ResponsePage<>();

        GenericSpecificationBuilder<AllowanceEntity> builder = new GenericSpecificationBuilder<>();

        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive !=null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }
        Specification<AllowanceEntity> spec = builder.build();

        Page<AllowanceEntity>  page = allowanceRepository.findAll(spec, pageable);

        responsePage.setContent(page.getContent().stream().map(allowanceMapper::toGetAllDto).toList());
        responsePage.setPage(page.getNumber());
        responsePage.setSort(page.getSort().toString());
        responsePage.setTotalElements(page.getTotalElements());
        responsePage.setTotalPages(page.getTotalPages());
        responsePage.setNumberOfElements(page.getNumberOfElements());

        response.setData(responsePage);
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public void deleteAllowance(Long id) {
        Optional<AllowanceEntity> allowanceEntity = allowanceRepository.findById(id);
        if (allowanceEntity.isPresent()){
            allowanceRepository.delete(allowanceEntity.get());
        }
    }

    @Override
    public BaseResponse<AllowanceDTO> findById(Long id) {
        BaseResponse<AllowanceDTO> response = new BaseResponse<>();
        Optional<AllowanceEntity> allowanceEntity = allowanceRepository.findById(id);
        if (allowanceEntity.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + id + " does not exist");
        }
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        response.setData(allowanceMapper.toDto(allowanceEntity.get()));
        return response;
    }


}
