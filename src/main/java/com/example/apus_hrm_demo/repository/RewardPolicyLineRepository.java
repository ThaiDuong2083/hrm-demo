package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.RewardPolicyLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RewardPolicyLineRepository extends JpaRepository<RewardPolicyLineEntity, Long>, JpaSpecificationExecutor<RewardPolicyLineEntity> {
    @Query("select a from RewardPolicyLineEntity a where a.rewardPolicyId =:rewardPolicyId")
    List<RewardPolicyLineEntity> findByRewardPolicyId(Long rewardPolicyId);
    @Query("select r.id from RewardPolicyLineEntity r where r.rewardPolicyId =:rewardPolicyId")
    List<Long> findAllIdByRewardPolicy(Long rewardPolicyId);

    @Query("select r from RewardPolicyLineEntity r where r.id in :ids")
    List<RewardPolicyLineEntity> findByIds(Set<Long> ids);

    @Modifying
    @Query("delete from RewardPolicyLineEntity r where r.rewardPolicyId =:rewardPolicyId")
    void deleteByRewardPolicyId(Long rewardPolicyId);

    @Query("select r from RewardPolicyLineEntity r where r.rewardPolicyId in :rewardIds")
    List<RewardPolicyLineEntity> findByRewardIds(Set<Long> rewardIds);
}
