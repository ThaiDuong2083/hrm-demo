package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RewardPolicyRepository extends JpaRepository<RewardPolicyEntity, Long>, JpaSpecificationExecutor<RewardPolicyEntity> {
    @Query("select a from RewardPolicyEntity a where a.id =:rewardPolicyId")
    Optional<RewardPolicyEntity> findByRewardPolicyId(Long rewardPolicyId);
}
