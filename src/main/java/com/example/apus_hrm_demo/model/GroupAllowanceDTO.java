package com.example.apus_hrm_demo.model;

import com.example.apus_hrm_demo.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupAllowanceDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private BaseDTO parent;
    private Boolean isActive;
}
