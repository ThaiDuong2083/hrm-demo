package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.exception.NullEntityException;
import com.example.apus_hrm_demo.mapper.base.MapperNameCode;
import com.example.apus_hrm_demo.mapper.group_reward.GroupRewardMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardDTO;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardGetAllDTO;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupRewardImpl implements GroupRewardService {
    private final GroupRewardRepository groupRewardRepository;
    private final GroupRewardMapper groupRewardMapper;
    private final CommonResponseGenerator<GroupRewardDTO, GroupRewardGetAllDTO, GroupRewardEntity> commonResponseGenerator;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(GroupRewardDTO groupRewardDTO) {
        GroupRewardEntity groupRewardEntity = groupRewardMapper.toEntity(groupRewardDTO);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper.toDto(groupRewardEntity).getId());
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(GroupRewardDTO groupRewardDTO) {
        GroupRewardEntity groupRewardEntity = checkGroupRewardEntity(groupRewardDTO.getId());
        groupRewardMapper.toUpdateEntity(groupRewardDTO, groupRewardEntity);
        return commonResponseGenerator.returnCUResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardMapper.toDto(groupRewardEntity).getId());
    }

    private GroupRewardEntity checkGroupRewardEntity(Long groupRewardId) {
        Optional<GroupRewardEntity> groupRewardEntityOptional = groupRewardRepository.findById(groupRewardId);
        if (groupRewardEntityOptional.isEmpty()){
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.NOT_FOUND);
        }
        return groupRewardEntityOptional.get();
    }

    @Override
    public void delete(Long id) {
        try {
            groupRewardRepository.delete(checkGroupRewardEntity(id));
            groupRewardRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NullEntityException(TraceIdGenarator.getTraceId(), MessageResponseConstant.ERROR_DELETE);
        }
    }

    @Override
    public BaseResponse<GroupRewardDTO> findById(Long id) {
        GroupRewardEntity groupRewardEntity = checkGroupRewardEntity(id);
        GroupRewardDTO groupRewardDTO = groupRewardMapper.toDto(groupRewardEntity);
        setParent(groupRewardDTO);
        return commonResponseGenerator.returnReadResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardDTO);
    }

    @Override
    public BaseResponse<ResponsePage<GroupRewardGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        GenericSpecificationBuilder<GroupRewardEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupRewardEntity> spec = builder.build();
        Page<GroupRewardEntity> pageEntities = groupRewardRepository.findAll(spec, pageable);
        List<GroupRewardGetAllDTO> groupRewardGetAllDTOS = pageEntities.getContent().stream().map(groupRewardMapper::toGetAllDto).toList();

        return commonResponseGenerator.returnListResponse(TraceIdGenarator.getTraceId(), MessageResponseConstant.SUCCESS, groupRewardGetAllDTOS,pageEntities);
    }

    private void setParent(GroupRewardDTO groupRewardDTO) {
        MapperNameCode mapperNameCode = new MapperNameCode();
        groupRewardDTO.setParent(mapperNameCode.convert(groupRewardDTO.getParent().getId(),groupRewardRepository));
    }
}
