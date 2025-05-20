package com.example.apus_hrm_demo.service;

import com.example.apus_hrm_demo.entity.call_other_sv.BaseOtherSVEntity;
import com.example.apus_hrm_demo.util.enum_util.ApplicableType;

import java.util.List;

public interface ApplicableTargetService {
    List<BaseOtherSVEntity> getApplicableTargets(List<Long>ids, ApplicableType applicableType);
}
