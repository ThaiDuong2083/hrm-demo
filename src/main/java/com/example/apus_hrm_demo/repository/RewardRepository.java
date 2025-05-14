package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RewardRepository extends JpaRepository<RewardEntity, Long> , JpaSpecificationExecutor<RewardEntity> {
    @Query("select r.id from  RewardEntity r where r.id =:id")
    Optional<Long> checkExistId(Long id);
}
