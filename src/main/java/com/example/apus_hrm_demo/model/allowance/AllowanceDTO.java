package com.example.apus_hrm_demo.model.allowance;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import com.example.apus_hrm_demo.util.AllowanceRewardType;
import com.example.apus_hrm_demo.util.DeductionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AllowanceDTO {
    private Long id;
    private String name;
    private String code;
    private Set<DeductionType> includeType;
    private BaseDTO groupAllowance;
    private BaseDTO currency;
    private BaseDTO uom;
    private AllowanceRewardType type;
    private String description;
    private Boolean isActive;
}
