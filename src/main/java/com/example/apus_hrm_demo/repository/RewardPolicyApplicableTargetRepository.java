package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardPolicyApplicableTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardPolicyApplicableTargetRepository extends JpaRepository<RewardPolicyApplicableTargetEntity, Long>, JpaSpecificationExecutor<RewardPolicyApplicableTargetEntity> {
    @Query("select a from RewardPolicyApplicableTargetEntity a where a.rewardPolicyId =:rewardPolicyId")
    List<RewardPolicyApplicableTargetEntity> findByRewardPolicyId(Long rewardPolicyId);
}
