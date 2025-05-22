package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRewardRepository extends BaseRepository<GroupRewardEntity> ,JpaRepository<GroupRewardEntity, Long>, JpaSpecificationExecutor<GroupRewardEntity> {
    @Override
    @Query("select g from GroupRewardEntity g where g.id in :ids")
    List<GroupRewardEntity> findByIds(Set<Long> ids);
}
