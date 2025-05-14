package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.mapper.group_reward.GroupRewardMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.GroupRewardDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.repository.GroupRewardRepository;
import com.example.apus_hrm_demo.service.GroupRewardService;
import com.example.apus_hrm_demo.speficiation.GenericSpecificationBuilder;
import com.example.apus_hrm_demo.speficiation.SearchOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class GroupRewardImpl implements GroupRewardService {
    private final GroupRewardRepository groupRewardRepository;
    private final GroupRewardMapper groupRewardMapper;
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BaseResponse<GroupRewardDTO> create(GroupRewardDTO groupRewardDTO) {
        BaseResponse<GroupRewardDTO> response = new BaseResponse<>();
        GroupRewardEntity groupRewardEntity = groupRewardMapper.toEntity(groupRewardDTO);
        GroupRewardDTO newGroupRewardDTO = groupRewardMapper.toDto(groupRewardRepository.save(groupRewardEntity));
        response.setData(newGroupRewardDTO);
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<GroupRewardDTO> update(GroupRewardDTO groupRewardDTO) {
        BaseResponse<GroupRewardDTO> response = new BaseResponse<>();
        Optional<GroupRewardEntity> oldGroupRewardEntity = groupRewardRepository.findById(groupRewardDTO.getId());
        if (oldGroupRewardEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group reward not found");
            return response;
        }

        GroupRewardEntity groupRewardEntity = oldGroupRewardEntity.get();
        groupRewardMapper.toUpdateEntity(groupRewardDTO, groupRewardEntity);

        response.setData(groupRewardMapper.toDto(groupRewardRepository.save(groupRewardEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public void delete(Long id) {
        Optional<GroupRewardEntity> groupRewardEntity = groupRewardRepository.findById(id);
        if (groupRewardEntity.isPresent()) {
            groupRewardRepository.delete(groupRewardEntity.get());
        }
    }

    @Override
    public BaseResponse<GroupRewardDTO> findById(Long id) {
        BaseResponse<GroupRewardDTO> response = new BaseResponse<>();
        Optional<GroupRewardEntity> groupRewardEntity = groupRewardRepository.findById(id);
        if (groupRewardEntity.isEmpty()) {
            response.setData(null);
            response.setTraceId(HttpStatus.NOT_FOUND.toString());
            response.setMessage("Group reward not found");
            return response;
        }
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        response.setData(groupRewardMapper.toDto(groupRewardEntity.get()));
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<GroupRewardDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        BaseResponse<ResponsePage<GroupRewardDTO>> response = new BaseResponse<>();

        GenericSpecificationBuilder<GroupRewardEntity> builder = new GenericSpecificationBuilder<>();

        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive != null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<GroupRewardEntity> spec = builder.build();
        Page<GroupRewardEntity> pageEntities = groupRewardRepository.findAll(spec, pageable);
        List<GroupRewardDTO> groupRewardDTOS = pageEntities.getContent().stream().map(groupRewardMapper::toDto).toList();

        ResponsePage<GroupRewardDTO> responsePage = new ResponsePage<>();
        responsePage.setContent(groupRewardDTOS);
        responsePage.setPage(pageEntities.getNumber());
        responsePage.setTotalPages(pageEntities.getTotalPages());
        responsePage.setSize(pageEntities.getSize());
        responsePage.setNumberOfElements(pageEntities.getNumberOfElements());
        responsePage.setSort(pageEntities.getSort().toString());

        response.setData(responsePage);
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

}
