package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowancePolicyLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AllowancePolicyLineRepository extends JpaRepository<AllowancePolicyLineEntity, Long>, JpaSpecificationExecutor<AllowancePolicyLineEntity> {
    @Query("select a from AllowancePolicyLineEntity a where a.allowancePolicyId =:allowancePolicyId")
    List<AllowancePolicyLineEntity> findByAllowancePolicyId(Long allowancePolicyId);
    @Query("select a from AllowancePolicyLineEntity a where a.allowanceId =:allowanceId")
    List<AllowancePolicyLineEntity> findByAllowanceId(Long allowanceId);
    @Query("select a.id from AllowancePolicyLineEntity a where a.allowancePolicyId =:allowancePolicyId")
    List<Long> findAllIdByAllowancePolicy(Long allowancePolicyId);
    @Modifying
    @Query("delete from AllowancePolicyLineEntity a where a.allowancePolicyId = :allowancePolicyId")
    void deleteByAllowancePolicyId(Long allowancePolicyId);

    @Query("select a from  AllowancePolicyLineEntity a where a.allowanceId in :allowanceIds ")
    List<AllowancePolicyLineEntity> findByAllowanceIds(Set<Long> allowanceIds);
}
