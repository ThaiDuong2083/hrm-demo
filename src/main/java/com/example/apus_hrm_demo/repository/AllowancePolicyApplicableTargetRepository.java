package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowancePolicyApplicableTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllowancePolicyApplicableTargetRepository extends JpaRepository<AllowancePolicyApplicableTargetEntity, Long>, JpaSpecificationExecutor<AllowancePolicyApplicableTargetEntity> {
    @Query("select a from AllowancePolicyApplicableTargetEntity a where a.allowancePolicyId =:allowancePolicyId")
    List<AllowancePolicyApplicableTargetEntity> findByAllowancePolicyId(Long allowancePolicyId);
}
