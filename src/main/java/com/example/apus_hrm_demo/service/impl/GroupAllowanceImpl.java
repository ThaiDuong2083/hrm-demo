package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.group_allowance.GroupAllowanceMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.group_allowance.GroupAllowanceDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupAllowanceImpl implements GroupAllowanceService {
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final GroupAllowanceMapper groupAllowanceMapper;
    private final CommonResponseGenerator<GroupAllowanceEntity, GroupAllowanceDTO, GroupAllowanceDTO, ResponseAfterCUDTO> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(GroupAllowanceDTO groupAllowanceDTO) {
        GroupAllowanceEntity groupAllowanceEntity = groupAllowanceMapper.toEntity(groupAllowanceDTO);
        return commonResponseGenerator.returnCUResponse(groupAllowanceEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceMapper);

    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(GroupAllowanceDTO groupAllowanceDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<GroupAllowanceEntity> oldGroupAllowanceEntity = groupAllowanceRepository.findById(groupAllowanceDTO.getId());
        if (oldGroupAllowanceEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group line not found");
            return response;
        }
        GroupAllowanceEntity groupAllowanceEntity = oldGroupAllowanceEntity.get();
        groupAllowanceMapper.toUpdateEntity(groupAllowanceDTO, groupAllowanceEntity);

        return commonResponseGenerator.returnCUResponse(groupAllowanceEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceMapper);
    }

    @Override
    public void delete(Long id) {
        Optional<GroupAllowanceEntity> groupAllowanceEntity = groupAllowanceRepository.findById(id);
        if (groupAllowanceEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        try {
            groupAllowanceRepository.delete(groupAllowanceEntity.get());
            groupAllowanceRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<GroupAllowanceDTO> findById(Long id) {
        Optional<GroupAllowanceEntity> groupAllowanceEntity = groupAllowanceRepository.findById(id);
        if (groupAllowanceEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return commonResponseGenerator.returnReadResponse(groupAllowanceEntity.get(), TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceMapper);
    }

    @Override
    public BaseResponse<ResponsePage<GroupAllowanceDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<GroupAllowanceEntity> builder = new GenericSpecificationBuilder<>();

        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupAllowanceEntity> spec = builder.build();
        Page<GroupAllowanceEntity> pageEntities = groupAllowanceRepository.findAll(spec, pageable);
        return commonResponseGenerator.returnListResponse(pageEntities, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupAllowanceMapper);
    }

}
