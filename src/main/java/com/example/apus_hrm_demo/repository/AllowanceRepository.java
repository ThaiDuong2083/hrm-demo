package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.AllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AllowanceRepository extends BaseRepository<AllowanceEntity>, JpaRepository<AllowanceEntity, Long>, JpaSpecificationExecutor<AllowanceEntity> {
    @Override
    @Query("select a from AllowanceEntity a where a.id in :ids")
    List<AllowanceEntity> findByIds(Set<Long> ids);
}
