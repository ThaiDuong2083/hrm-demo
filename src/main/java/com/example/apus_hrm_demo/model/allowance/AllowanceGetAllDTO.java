package com.example.apus_hrm_demo.model.allowance;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowanceGetAllDTO {
    private Long id;
    private String name;
    private String code;
    private BaseDTO groupAllowance;
    private String description;
    private Boolean isActive;
}
