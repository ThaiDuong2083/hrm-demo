package com.example.apus_hrm_demo.service.impl;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.mapper.GenericReponseAfterCUMapper;
import com.example.apus_hrm_demo.mapper.reward.RewardMapper;
import com.example.apus_hrm_demo.model.base.BaseResponse;
import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import com.example.apus_hrm_demo.model.base.ResponsePage;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import com.example.apus_hrm_demo.repository.RewardRepository;
import com.example.apus_hrm_demo.service.RewardService;
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
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    private final RewardMapper rewardMapper;
    private final GenericReponseAfterCUMapper<RewardEntity> genericReponseAfterCUMapper;

    @Override
    public BaseResponse<ResponseAfterCUDTO> createReward(RewardDTO rewardFormDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<Long> check = rewardRepository.checkExistId(rewardFormDTO.getId());
        if (check.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + rewardFormDTO.getId() + " does not exist");
            return response;
        }

        RewardEntity rewardEntity = rewardMapper.toEntity(rewardFormDTO);
        response.setData(genericReponseAfterCUMapper.toDto(rewardRepository.save(rewardEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<ResponseAfterCUDTO> updateReward(RewardDTO rewardFormDTO) {
        BaseResponse<ResponseAfterCUDTO> response = new BaseResponse<>();
        Optional<RewardEntity> check = rewardRepository.findById(rewardFormDTO.getId());
        if (check.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + rewardFormDTO.getId() + " does not exist");
            return response;
        }

        RewardEntity newRewardEntity = check.get();
        rewardMapper.toUpdateEntity(rewardFormDTO, newRewardEntity);

        response.setData(genericReponseAfterCUMapper.toDto(rewardRepository.save(newRewardEntity)));
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        return response;
    }

    @Override
    public BaseResponse<ResponsePage<RewardGetAllDTO>> getAll(String name, Boolean isActive, Pageable pageable) {
        BaseResponse<ResponsePage<RewardGetAllDTO>> response = new BaseResponse<>();
        ResponsePage<RewardGetAllDTO> responsePage = new ResponsePage<>();

        GenericSpecificationBuilder<RewardEntity> builder = new GenericSpecificationBuilder<>();
        if (name != null && !name.isEmpty()) {
            builder.with("name,code", SearchOperation.MULTI_FIELD_CONTAINS, name, false);
        }
        if (isActive !=null) {
            builder.with("isActive", SearchOperation.EQUALITY, isActive, false);
        }

        Specification<RewardEntity> spec = builder.build();
        Page<RewardEntity>  page = rewardRepository.findAll(spec, pageable);

        responsePage.setContent(page.getContent().stream().map(rewardMapper::toGetAllDto).toList());
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
    public void deleteReward(Long id) {
        Optional<RewardEntity> rewardEntity = rewardRepository.findById(id);
        if (rewardEntity.isPresent()){
            rewardRepository.delete(rewardEntity.get());
        }
    }

    @Override
    public BaseResponse<RewardDTO> findById(Long id) {
        BaseResponse<RewardDTO> response = new BaseResponse<>();
        Optional<RewardEntity> rewardEntity = rewardRepository.findById(id);
        if (rewardEntity.isEmpty()){
            response.setData(null);
            response.setTraceId(HttpStatus.BAD_REQUEST.toString());
            response.setMessage("The id " + id + " does not exist");
        }
        response.setTraceId(HttpStatus.OK.toString());
        response.setMessage("Success");
        response.setData(rewardMapper.toDto(rewardEntity.get()));
        return response;
    }


}
