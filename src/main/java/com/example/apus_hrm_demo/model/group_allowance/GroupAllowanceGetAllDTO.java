package com.example.apus_hrm_demo.model.group_allowance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupAllowanceGetAllDTO {
    private Long id;
    private String name;
    private String code;
    private Boolean isActive;
}
