package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.group_reward.GroupRewardMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardDTO;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import com.example.apus_hrm_demo.service.GroupRewardService;
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
public class GroupRewardImpl implements GroupRewardService {
    private final GroupRewardRepository groupRewardRepository;
    private final GroupRewardMapper groupRewardMapper;
    private final CommonResponseGenerator<GroupRewardEntity, GroupRewardDTO, GroupRewardDTO, ResponseAfterCUDTO> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(GroupRewardDTO groupRewardDTO) {
        GroupRewardEntity groupRewardEntity = groupRewardMapper.toEntity(groupRewardDTO);
        return commonResponseGenerator.returnCUResponse(groupRewardEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper);
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(GroupRewardDTO groupRewardDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<GroupRewardEntity> oldGroupRewardEntity = groupRewardRepository.findById(groupRewardDTO.getId());
        if (oldGroupRewardEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group line not found");
            return response;
        }

        GroupRewardEntity groupRewardEntity = oldGroupRewardEntity.get();
        groupRewardMapper.toUpdateEntity(groupRewardDTO, groupRewardEntity);
        return commonResponseGenerator.returnCUResponse(groupRewardEntity, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper);
    }

    @Override
    public void delete(Long id) {
        Optional<GroupRewardEntity> groupRewardEntity = groupRewardRepository.findById(id);
        if (groupRewardEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        try {
            groupRewardRepository.delete(groupRewardEntity.get());
            groupRewardRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<GroupRewardDTO> findById(Long id) {
        Optional<GroupRewardEntity> groupRewardEntity = groupRewardRepository.findById(id);
        if (groupRewardEntity.isEmpty()) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return commonResponseGenerator.returnReadResponse(groupRewardEntity.get(), TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper);
    }

    @Override
    public BaseResponse<ResponsePage<GroupRewardDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<GroupRewardEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupRewardEntity> spec = builder.build();
        Page<GroupRewardEntity> pageEntities = groupRewardRepository.findAll(spec, pageable);

        return commonResponseGenerator.returnListResponse(pageEntities, TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper);
    }

}
