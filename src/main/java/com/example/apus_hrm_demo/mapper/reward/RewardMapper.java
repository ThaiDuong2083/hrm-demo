package com.example.apus_hrm_demo.mapper.reward;

import com.example.apus_hrm_demo.entity.RewardEntity;
import com.example.apus_hrm_demo.model.reward.RewardDTO;
import com.example.apus_hrm_demo.model.reward.RewardGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ConvertInReward.class)
public interface RewardMapper {
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToDto")
    @Mapping(source = "currencyId", target = "currency.id")
    @Mapping(source = "uomId", target = "uom.id")
    @Mapping(source = "groupRewardId", target = "groupReward.id")
    RewardDTO toDto(RewardEntity rewardEntity);


    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "groupReward.id",target = "groupRewardId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    RewardEntity toEntity(RewardDTO rewardFormDTO);

    @Mapping(source = "groupRewardId", target = "groupReward.id")
    RewardGetAllDTO toGetAllDto(RewardEntity rewardEntity);

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "groupRewardId", source = "groupReward.id")
    void toUpdateEntity(RewardDTO rewardDTO, @MappingTarget RewardEntity rewardEntity);
}
