package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Long>, JpaSpecificationExecutor<PayrollEntity> {
}
