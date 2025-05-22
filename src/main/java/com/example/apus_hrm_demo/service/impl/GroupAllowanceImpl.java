package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.group_allowance.GroupAllowanceMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceDTO;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceGetAllDTO;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import com.example.apus_hrm_demo.service.GroupAllowanceService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.util.TraceIdGenarator;
import com.example.apus_hrm_demo.util.constant.MessageResponseConstant;
import com.example.apus_hrm_demo.util.enum_util.SearchOperation;
import com.example.apus_hrm_demo.util.response.CommonResponseGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupAllowanceImpl implements GroupAllowanceService {
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final GroupAllowanceMapper groupAllowanceMapper;
    private final CommonResponseGenerator<GroupAllowanceDTO, GroupAllowanceGetAllDTO, GroupAllowanceEntity> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(GroupAllowanceDTO groupAllowanceDTO) {
        GroupAllowanceEntity groupAllowanceEntity = groupAllowanceMapper.toEntity(groupAllowanceDTO);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceMapper.toDto(groupAllowanceEntity).getId());

    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(GroupAllowanceDTO groupAllowanceDTO) {
        GroupAllowanceEntity groupAllowanceEntity = checkGroupAllowance(groupAllowanceDTO.getId());
        groupAllowanceMapper.toUpdateEntity(groupAllowanceDTO, groupAllowanceEntity);

        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceEntity.getId());
    }

    private GroupAllowanceEntity checkGroupAllowance(Long id) {
        Optional<GroupAllowanceEntity> groupAllowanceEntityOptional = groupAllowanceRepository.findById(id);
        if (groupAllowanceEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(),MessageResponseConstant.NOT_FOUND);
        }
        return groupAllowanceEntityOptional.get();
    }

    @Override
    public void delete(Long id) {
        try {
            groupAllowanceRepository.delete(checkGroupAllowance(id));
            groupAllowanceRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<GroupAllowanceDTO> findById(Long id) {
        GroupAllowanceEntity groupAllowanceEntity = checkGroupAllowance(id);
        GroupAllowanceDTO groupAllowanceDTO = groupAllowanceMapper.toDto(groupAllowanceEntity);
        setParent(groupAllowanceDTO);
        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceDTO);
    }

    @Override
    public BaseResponse<ResponsePage<GroupAllowanceGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<GroupAllowanceEntity> builder = new GenericSpecificationBuilder<>();

        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupAllowanceEntity> spec = builder.build();
        Page<GroupAllowanceEntity> pageEntities = groupAllowanceRepository.findAll(spec, pageable);
        List<GroupAllowanceGetAllDTO> allowanceGetAllDTOS = pageEntities.getContent().stream().map(groupAllowanceMapper::toGetAllDto).toList();
        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, allowanceGetAllDTOS, pageEntities);
    }

    private void setParent(GroupAllowanceDTO groupAllowanceDTO) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        groupAllowanceDTO.setParent(mapperNameCode.convert(groupAllowanceDTO.getParent().getId(),groupAllowanceRepository));
    }
}
