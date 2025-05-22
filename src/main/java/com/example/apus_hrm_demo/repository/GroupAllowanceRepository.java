package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.GroupAllowanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupAllowanceRepository extends BaseRepository<GroupAllowanceEntity>,JpaRepository<GroupAllowanceEntity, Long>, JpaSpecificationExecutor<GroupAllowanceEntity> {
    @Override
    @Query("select g from GroupAllowanceEntity g where g.id in :ids")
    List<GroupAllowanceEntity> findByIds(Set<Long> ids);
}
