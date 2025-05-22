package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AllowancePolicyApplicableTargetRepository extends BaseRepository<AllowancePolicyApplicableTargetEntity>,JpaRepository<AllowancePolicyApplicableTargetEntity, Long>, JpaSpecificationExecutor<AllowancePolicyApplicableTargetEntity> {
    @Query("select a from AllowancePolicyApplicableTargetEntity a where a.allowancePolicyId =:allowancePolicyId")
    List<AllowancePolicyApplicableTargetEntity> findByAllowancePolicyId(Long allowancePolicyId);

    @Query("select a from AllowancePolicyApplicableTargetEntity a where a.id in :id")
    List<AllowancePolicyApplicableTargetEntity> findByIds(Set<Long> ids);

    List<AllowancePolicyApplicableTargetEntity> id(Long id);

    @Modifying
    @Query("delete from AllowancePolicyApplicableTargetEntity a where a.id =:allowancePolicyId")
    void deleteByAllowancePolicyId(Long allowancePolicyId);
}

