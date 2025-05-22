package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RewardRepository extends BaseRepository<RewardEntity>, JpaRepository<RewardEntity, Long> , JpaSpecificationExecutor<RewardEntity> {
    @Query("select r from RewardEntity r where r.id in :ids")
    List<RewardEntity> findByIds(Set<Long> ids);
}
