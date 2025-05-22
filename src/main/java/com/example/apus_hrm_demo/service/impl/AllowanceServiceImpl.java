package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.allowance.AllowanceMapper;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.model.allowance.AllowanceDTO;
import com.example.apus_hrm_demo.model.allowance.AllowanceGetAllDTO;
import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.AllowanceRepository;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import com.example.apus_hrm_demo.service.AllowanceSercive;
import com.example.apus_hrm_demo.service.ExtenalService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import com.example.apus_hrm_demo.util.response.CommonResponseGeneratorImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AllowanceServiceImpl implements AllowanceSercive {
    private final AllowanceRepository allowanceRepository;
    private final AllowanceMapper allowanceMapper;
    private final ExtenalService extenalService;
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final CommonResponseGeneratorImpl<AllowanceDTO,AllowanceGetAllDTO, AllowanceEntity> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createAllowance(AllowanceDTO allowanceFormDTO) {
        AllowanceEntity allowanceEntity = allowanceRepository.save( allowanceMapper.toEntity(allowanceFormDTO));
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(),
                                                        MessageResponseConstant.SUCCESS,
                                                allowanceMapper.toDto(allowanceEntity).getId());
    }

    private AllowanceEntity checkEntity(Long id){
        Optional<AllowanceEntity> check = allowanceRepository.findById(id);
        if (check.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND );
        }
        return check.get();
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateAllowance(AllowanceDTO allowanceFormDTO) {
        AllowanceEntity allowanceEntity = checkEntity(allowanceFormDTO.getId());

        allowanceMapper.toUpdateEntity(allowanceFormDTO,allowanceEntity);
        allowanceRepository.save(allowanceEntity);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(),
                                                        MessageResponseConstant.SUCCESS,
                                                        allowanceEntity.getId());
    }

    @Override
    public BaseResponse<ResponsePage<AllowanceGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<AllowanceEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive !=null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }
        Specification<AllowanceEntity> spec = builder.build();
        Page<AllowanceEntity>  page = allowanceRepository.findAll(spec, pageable);
        Map<Long, BaseDTO> groupAllowances = listGroupAllowance(page.getContent().stream().map(AllowanceEntity::getId).collect(Collectors.toSet()));
        List<AllowanceGetAllDTO> allowanceGetAllDTOS = page.getContent().stream().map(entity->{
            AllowanceGetAllDTO allowanceGetAllDTO = allowanceMapper.toGetAllDto(entity);
            allowanceGetAllDTO.setGroupAllowance(groupAllowances.get(entity.getId()));
            return allowanceGetAllDTO;
        }).toList();

        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,allowanceGetAllDTOS,page);
    }

    @Override
    public void deleteAllowance(Long id) {
        try {
            allowanceRepository.delete(checkEntity(id));
            allowanceRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE );
        }
    }

    @Override
    public BaseResponse<AllowanceDTO> findById(Long id) {
        AllowanceEntity allowanceEntity = checkEntity(id);
        AllowanceDTO allowanceDTO = allowanceMapper.toDto(allowanceEntity);
        setUomAndCurrency(allowanceDTO);
        allowanceDTO.setGroupAllowance(groupAllowance(allowanceDTO.getGroupAllowance().getId()));
        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS,allowanceDTO);
    }

    private void setUomAndCurrency(AllowanceDTO allowanceDTO){
        List<BaseDTO> currencies = extenalService.getCurrency(Set.of(allowanceDTO.getCurrency().getId()));
        List<BaseDTO> uoms = extenalService.getUom(Set.of(allowanceDTO.getUom().getId()));
        allowanceDTO.setCurrency(currencies.getFirst());
        allowanceDTO.setUom(uoms.getFirst());
    }

    private BaseDTO groupAllowance(Long groupId){
        MapperNameCode mapperNameCode = new MapperNameCode();
        return mapperNameCode.convert(groupId, allowanceRepository);
    }
    private Map<Long, BaseDTO> listGroupAllowance (Set<Long> groupId){
        MapperNameCode mapperNameCode = new MapperNameCode();
        List<BaseDTO> baseDTOS = mapperNameCode.convert(groupId, groupAllowanceRepository);
        Map<Long, BaseDTO> groupAllowances = new HashMap<>();
        baseDTOS.forEach(allowanceDTO->groupAllowances.put(allowanceDTO.getId(), allowanceDTO));
        return groupAllowances;
    }

}
