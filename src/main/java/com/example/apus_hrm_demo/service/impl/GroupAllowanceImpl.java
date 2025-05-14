package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import com.example.apus_hrm_demo.mapper.GenericReponseAfterCUMapper;
import com.example.apus_hrm_demo.mapper.group_allowance.GroupAllowanceMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.GroupAllowanceDTO;
import com.example.apus_hrm_demo.repository.GroupAllowanceRepository;
import com.example.apus_hrm_demo.service.GroupAllowanceService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.speficiation.SearchOperation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupAllowanceImpl implements GroupAllowanceService {
    private final GroupAllowanceRepository groupAllowanceRepository;
    private final GroupAllowanceMapper groupAllowanceMapper;
    private final GenericReponseAfterCUMapper<GroupAllowanceEntity> genericReponseAfterCUMapper;

    @Override
    public BaseResponse<ResponseAfterCUDTO> create(GroupAllowanceDTO groupAllowanceDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        GroupAllowanceEntity groupAllowanceEntity = groupAllowanceMapper.toEntity(groupAllowanceDTO);
        response.setData(genericReponseAfterCUMapper.toDto(groupAllowanceRepository.save(groupAllowanceEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> update(GroupAllowanceDTO groupAllowanceDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<GroupAllowanceEntity> oldGroupAllowanceEntity = groupAllowanceRepository.findById(groupAllowanceDTO.getId());
        if (oldGroupAllowanceEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group reward not found");
            return response;
        }
        GroupAllowanceEntity groupAllowanceEntity = oldGroupAllowanceEntity.get();
        groupAllowanceMapper.toUpdateEntity(groupAllowanceDTO, groupAllowanceEntity);

        response.setData(genericReponseAfterCUMapper.toDto(groupAllowanceRepository.save(groupAllowanceEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public void delete(Long id) {
        Optional<GroupAllowanceEntity> groupAllowanceEntity = groupAllowanceRepository.findById(id);
        if (groupAllowanceEntity.isPresent()) {
            groupAllowanceRepository.delete(groupAllowanceEntity.get());
        }
    }

    @Override
    public BaseResponse<GroupAllowanceDTO> findById(Long id) {
        BaseResponse<GroupAllowanceDTO> response = new BaseResponse<>();
        Optional<GroupAllowanceEntity> groupAllowanceEntity = groupAllowanceRepository.findById(id);
        if (groupAllowanceEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group reward not found");
            return response;
        }
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        response.setData(groupAllowanceMapper.toDto(groupAllowanceEntity.get()));
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<GroupAllowanceDTO>> getAll(String name, Boolean isActive,Pageable pageable) {
        BaseResponse<ResponsePage<GroupAllowanceDTO>> response = new BaseResponse<>();

        GenericSpecificationBuilder<GroupAllowanceEntity> builder = new GenericSpecificationBuilder<>();

        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupAllowanceEntity> spec = builder.build();
        Page<GroupAllowanceEntity> pageEntities = groupAllowanceRepository.findAll(spec, pageable);
        List<GroupAllowanceDTO> groupAllowanceDTOS = pageEntities.getContent().stream().map(groupAllowanceMapper::toDto).toList();

        ResponsePage<GroupAllowanceDTO> responsePage = new ResponsePage<>();
        responsePage.setContent(groupAllowanceDTOS);
        responsePage.setPage(pageEntities.getNumber());
        responsePage.setTotalPages(pageEntities.getTotalPages());
        responsePage.setSize(pageEntities.getSize());
        responsePage.setTotalElements(pageEntities.getTotalElements());
        responsePage.setNumberOfElements(pageEntities.getNumberOfElements());
        responsePage.setSort(pageEntities.getSort().toString());

        response.setData(responsePage);
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

}
