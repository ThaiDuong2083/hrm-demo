package com.example.apus_hrm_demo.mapper.group_reward;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import com.example.apus_hrm_demo.model.GroupRewardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ParentGroupRewardMapper.class)
public interface GroupRewardMapper {
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "parentIdRewardToParentDto")
    GroupRewardDTO toDto(GroupRewardEntity groupRewardEntity);

    @Mapping(source = "parent.id",target = "parentId")
    GroupRewardEntity toEntity(GroupRewardDTO groupRewardDTO);

    @Mapping(source = "parent.id",target = "parentId")
    void toUpdateEntity(GroupRewardDTO groupRewardDTO, @MappingTarget GroupRewardEntity groupRewardEntity);
}
