package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.PayrollLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayrollLineRepository extends JpaRepository<PayrollLineEntity, Long>, JpaSpecificationExecutor<PayrollLineEntity> {
    @Query("select p from PayrollLineEntity p where p.payrollId =:payrollId")
    List<PayrollLineEntity> findByPayrollId(Long payrollId);
}
