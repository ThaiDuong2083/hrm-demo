package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AllowanceRepository extends JpaRepository<AllowanceEntity, Long>, JpaSpecificationExecutor<AllowanceEntity> {
    @Query("select a.id from  AllowanceEntity a where a.id =:id")
    Optional<Long> checkExistId(Long id);
}
