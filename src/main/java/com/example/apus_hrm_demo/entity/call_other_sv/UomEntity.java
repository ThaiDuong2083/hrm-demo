package com.example.apus_hrm_demo.entity.call_other_sv;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class UomEntity extends BaseOtherSVEntity{
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
