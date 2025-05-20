package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardPolicyLineRepository extends JpaRepository<RewardPolicyLineEntity, Long>, JpaSpecificationExecutor<RewardPolicyLineEntity> {
    @Query("select a from RewardPolicyLineEntity a where a.rewardPolicyId =:rewardPolicyId")
    List<RewardPolicyLineEntity> findByRewardPolicyId(Long rewardPolicyId);
    @Query("select a from RewardPolicyLineEntity a where a.rewardId =:rewardId")
    List<RewardPolicyLineEntity> findByRewardId(Long rewardId);
}
