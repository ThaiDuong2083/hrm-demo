package com.example.apus_hrm_demo.repository;

import com.example.apus_hrm_demo.entity.GroupRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRewardRepository extends JpaRepository<GroupRewardEntity, Long>, JpaSpecificationExecutor<GroupRewardEntity> {
}
