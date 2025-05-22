package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AllowancePolicyRepository extends JpaRepository<AllowancePolicyEntity, Long>, JpaSpecificationExecutor<AllowancePolicyEntity> {
    @Query("select a from AllowancePolicyEntity a where a.id =:allowancePolicyId")
    Optional<AllowancePolicyEntity> findByAllowancePolicyId(Long allowancePolicyId);
}
