package com.example.apus_hrm_demo.mapper.group_reward;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardDTO;
import com.example.apus_hrm_demo.model.group_reward.GroupRewardGetAllDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupRewardMapper {
    @Mapping(source = "parentId", target = "parent.id")
    GroupRewardDTO toDto(GroupRewardEntity groupRewardEntity);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    GroupRewardEntity toEntity(GroupRewardDTO groupRewardDTO);

    @Mapping(source = "parent.id",target = "parentId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void toUpdateEntity(GroupRewardDTO groupRewardDTO, @MappingTarget GroupRewardEntity groupRewardEntity);

    GroupRewardGetAllDTO toGetAllDto(GroupRewardEntity entity);
}
