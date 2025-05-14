package com.example.apus_hrm_demo.entity;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class UomEntity extends BaseEntity {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean activated;
    private Set<BaseDTO> uomGroup;
    private Long companyId;
    private Long orgId;
    private LocalDateTime createdAt;
    private Long createdBy;
    private BaseDTO userCreatedBy;
    private BaseDTO isUsing;
    private Boolean isSource;
    private Boolean existsInBranch;
}
