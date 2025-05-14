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
    @Mapping(source = "currencyId", target = "currency", qualifiedByName = "currencyToDTO")
    @Mapping(source = "uomId", target = "uom", qualifiedByName = "uomToDTO")
    @Mapping(source = "groupRewardId", target = "groupReward", qualifiedByName = "toGroupRewardDTO")
    RewardDTO toDto(RewardEntity rewardEntity);


    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    @Mapping(source = "groupReward.id",target = "groupRewardId")
    RewardEntity toEntity(RewardDTO rewardFormDTO);

    @Mapping(source = "groupRewardId", target = "groupReward", qualifiedByName = "toGroupRewardDTO")
    RewardGetAllDTO toGetAllDto(RewardEntity rewardEntity);

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "uom.id",target = "uomId")
    @Mapping(source = "includeType",target = "includeType", qualifiedByName = "includeTypeToEntity")
    void toUpdateEntity(RewardDTO rewardDTO, @MappingTarget RewardEntity rewardEntity);
}
